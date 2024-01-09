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
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FollowServiceImpl implements FollowService {

  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

  private final UserService userService;

  private final FollowerRepository followerRepository;

  @Override
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
            .follower_userid(firstUser.getUsername())
//            .followerUser(userService.getUserEntityByUsername(firstUser.getUsername()))
            .following_userid(secondUser.getUsername())
//            .followingUser(userService.getUserEntityByUsername(secondUser.getUsername()))
            .build())
        .filter(key -> !Objects.equals(key.getFollower_userid(), key.getFollowing_userid()))
        .orElseThrow(() -> new RuntimeException(
            "Failed to create FollowerKey. Because Two Username are the same"));
    if (followerRepository.findFollowerByFollowerIdAndFollowingId(followRequest.getFirstUsername(),
        followRequest.getSecondUsername()).size() > 0) {
      logger.error(
          ConstantMessage.USERNAME_IS_EXIST.getMessage() + ": " + followRequest.getFirstUsername()
              + " and " + followRequest.getSecondUsername());
      throw new CustomException(
          ConstantMessage.USERNAME_IS_EXIST.getMessage() + " : " + followRequest.getFirstUsername()
              + " and " + followRequest.getSecondUsername());
    }
//    Follower follower = new Follower();
//    follower.setFollowerKey(followerKey);
    followerRepository.insertFollower(followRequest.getFirstUsername(),
        followRequest.getSecondUsername());
//    Follower follower = followerRepository.findFollowerByFollowerIdAndFollowingId(
//        followRequest.getFirstUsername(), followRequest.getSecondUsername());
//    followerRepository.save(follower);
    return new FollowResponse(userService.convertToUserResponse(firstUser),
        userService.convertToUserResponse(secondUser));
  }

  @Override
  public List<UserResponse> getAllFollowerUser(String username)
      throws CustomException // List All User Follower the User
  {
    Optional.of(username)
        .filter(userId -> !userId.isEmpty() && !userId.isBlank())
        .orElseThrow(() -> handleInvalidUsername(username));

    User user = Optional.ofNullable(userService.getUserEntityByUsername(username))
        .orElseThrow(() -> handleUserNotFound(username));

    List<UserResponse> userResponseList = followerRepository.findAllFollowingUserByUsername(
            username)
        .stream()
        .map(following -> {
          try {
            return userService.convertToUserResponse(
                userService.getUserEntityByUsername(
                    following.getFollowerKey().getFollower_userid()));
          } catch (CustomException e) {
            throw new RuntimeException(e);
          }
        })
        .toList();
    return userResponseList;
  }

  @Override
  public List<UserResponse> getAllFollowingUser(String username)
      throws CustomException // List All User, User Following
  {
    Optional.of(username)
        .filter(userId -> !userId.isEmpty() && !userId.isBlank())
        .orElseThrow(() -> handleInvalidUsername(username));

    User user = Optional.ofNullable(userService.getUserEntityByUsername(username))
        .orElseThrow(() -> handleUserNotFound(username));

    List<UserResponse> userResponseList = followerRepository.findAllFollowerUserByUsername(username)
        .stream()
        .map(follower -> {
              try {
                return userService.convertToUserResponse(
                        userService.getUserEntityByUsername(
                            follower.getFollowerKey().getFollowing_userid()
                        )
                    );
              } catch (CustomException e) {
                throw new RuntimeException(e);
              }
            }
        )
        .toList();
    return userResponseList;
//    return null;
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
