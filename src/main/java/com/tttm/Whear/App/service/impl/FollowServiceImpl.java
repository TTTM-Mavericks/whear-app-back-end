package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.constant.ConstantMessage;
import com.tttm.Whear.App.entity.FollowerKey;
import com.tttm.Whear.App.entity.User;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.repository.FollowerRepository;
import com.tttm.Whear.App.service.FollowService;
import com.tttm.Whear.App.service.UserService;
import com.tttm.Whear.App.utils.request.FollowRequest;
import com.tttm.Whear.App.utils.response.FollowResponse;
import com.tttm.Whear.App.utils.response.UserResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService {

  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  private final UserService userService;

  private final FollowerRepository followerRepository;


  @Override
  @CacheEvict(cacheNames = {"follower", "following"}, allEntries = true)
  public FollowResponse userFollowAnotherUser(FollowRequest followRequest) throws CustomException {

    Optional.of(followRequest.getFirstUsername())
        .filter(username -> !username.isEmpty() && !username.isBlank())
        .orElseThrow(() -> handleInvalidUsername(followRequest.getFirstUsername()));

    Optional.of(followRequest.getSecondUsername())
        .filter(username -> !username.isBlank() && !username.isEmpty())
        .orElseThrow(() -> handleInvalidUsername(followRequest.getSecondUsername()));

    User firstUser = Optional.ofNullable(
            userService.getUserEntityByUsername(followRequest.getFirstUsername()))
        .orElseThrow(() -> handleUserNotFound(followRequest.getFirstUsername()));

    User secondUser = Optional.ofNullable(
            userService.getUserEntityByUsername(followRequest.getSecondUsername()))
        .orElseThrow(() -> handleUserNotFound(followRequest.getSecondUsername()));

    FollowerKey followerKey = Optional.of(FollowerKey
            .builder()
            .followerUser(userService.getUserEntityByUsername(firstUser.getUsername()))
            .followingUser(userService.getUserEntityByUsername(secondUser.getUsername()))
            .build())
        .filter(key -> !Objects.equals(key.getFollowerUser().getUserID(),
            key.getFollowingUser().getUserID()))
        .orElseThrow(() -> new RuntimeException(
            "Failed to create FollowerKey. Because Two Username are the same"));
    if (followerRepository.findFollowerByFollowerIdAndFollowingId(firstUser.getUserID(),
        secondUser.getUserID()).size() > 0) {
      logger.error(
          ConstantMessage.USERNAME_IS_EXIST.getMessage() + ": " + followRequest.getFirstUsername()
              + " and " + followRequest.getSecondUsername());
      throw new CustomException(
          ConstantMessage.USERNAME_IS_EXIST.getMessage() + " : " + followRequest.getFirstUsername()
              + " and " + followRequest.getSecondUsername());
    }
    followerRepository.insertFollower(firstUser.getUserID(),
        secondUser.getUserID());
    return new FollowResponse(userService.convertToUserResponse(firstUser),
        userService.convertToUserResponse(secondUser));
  }

  @Override
  @Cacheable(cacheNames = "follower", key = "#username", condition = "#username != null", unless = "#result == null")
  public List<UserResponse> getAllFollowerUser(String username)
      throws CustomException // List All User Follower the User
  {
    Optional.of(username)
        .filter(userId -> !userId.isEmpty() && !userId.isBlank())
        .orElseThrow(() -> handleInvalidUsername(username));

    User user = Optional.ofNullable(userService.getUserEntityByUsername(username))
        .orElseThrow(() -> handleUserNotFound(username));

    List<UserResponse> userResponseList = followerRepository.findAllFollowingUserByUsername(
            user.getUserID())
        .stream()
        .map(following -> {
          try {
            return userService.convertToUserResponse(
                userService.getUserEntityByUsername(
                    following.getFollowerKey().getFollowerUser().getUsername()));
          } catch (CustomException e) {
            throw new RuntimeException(e);
          }
        })
        .toList();
    return userResponseList;
  }

  @Override
  @Cacheable(cacheNames = "following", key = "#username", condition = "#username != null", unless = "#result == null")
  public List<UserResponse> getAllFollowingUser(String username)
      throws CustomException // List All User, User Following
  {
    Optional.of(username)
        .filter(userId -> !userId.isEmpty() && !userId.isBlank())
        .orElseThrow(() -> handleInvalidUsername(username));

    User user = Optional.ofNullable(userService.getUserEntityByUsername(username))
        .orElseThrow(() -> handleUserNotFound(username));

    List<UserResponse> userResponseList = followerRepository.findAllFollowerUserByUsername(
            user.getUserID())
        .stream()
        .map(follower -> {
              try {
                return userService.convertToUserResponse(
                    userService.getUserEntityByUsername(
                        follower.getFollowerKey().getFollowingUser().getUsername()
                    )
                );
              } catch (CustomException e) {
                throw new RuntimeException(e);
              }
            }
        )
        .toList();
    return userResponseList;
  }

  private CustomException handleInvalidUsername(String username) {
    logger.error(ConstantMessage.USERNAME_IS_EMPTY_OR_NOT_EXIST.getMessage());
    return new CustomException(ConstantMessage.USERNAME_IS_EMPTY_OR_NOT_EXIST.getMessage());
  }

  private CustomException handleUserNotFound(String username) {
    logger.error(ConstantMessage.CANNOT_FIND_USER_BY_USERNAME.getMessage());
    return new CustomException(ConstantMessage.CANNOT_FIND_USER_BY_USERNAME.getMessage());
  }
}
