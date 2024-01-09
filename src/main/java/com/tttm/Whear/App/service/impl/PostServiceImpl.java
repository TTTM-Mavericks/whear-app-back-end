package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.constant.ConstantMessage;
import com.tttm.Whear.App.entity.Post;
import com.tttm.Whear.App.entity.User;
import com.tttm.Whear.App.enums.StatusGeneral;
import com.tttm.Whear.App.enums.TypeOfPosts;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.repository.PostRepository;
import com.tttm.Whear.App.service.PostService;
import com.tttm.Whear.App.service.UserService;
import com.tttm.Whear.App.utils.request.PostRequest;
import com.tttm.Whear.App.utils.response.PostResponse;
import com.tttm.Whear.App.utils.response.UserResponse;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
  private final PostRepository postRepository;
  private final UserService userService;

  private boolean checkValidArguement(PostRequest postRequest) {
    return !postRequest.getUserID().isEmpty() && !postRequest.getUserID().isBlank() &&
        !postRequest.getTypeOfPosts().toString().isEmpty() && !postRequest.getTypeOfPosts()
        .toString().isBlank() &&
        !postRequest.getHashtag().isEmpty() && !postRequest.getHashtag().isBlank();
  }

  @Override
  public PostResponse createPost(PostRequest postRequest) throws CustomException {
    if (!checkValidArguement(postRequest)) {
      logger.error(ConstantMessage.MISSING_ARGUMENT.getMessage());
      throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
    }
    UserResponse userResponse = userService.getUserbyUsername(postRequest.getUserID());
    if (userResponse == null) {
      logger.error(ConstantMessage.USERNAME_IS_EMPTY_OR_NOT_EXIST.getMessage());
      throw new CustomException(ConstantMessage.USERNAME_IS_EMPTY_OR_NOT_EXIST.getMessage());
    }
    Post post = Post
        .builder()
//        .userPost(userService.getUserEntityByUsername(postRequest.getUserID()))
        .typeOfPosts(postRequest.getTypeOfPosts())
//        .hashtag(postRequest.getHashtag())
        .status(StatusGeneral.ACTIVE)
        .date(new Date())
        .build();
    post = postRepository.save(post);
    logger.info(ConstantMessage.CREATE_SUCCESS.getMessage());
    return convertToPostResponse(post);
  }

  @Override
  public PostResponse getPostByPostID(Integer postID) throws CustomException {
    if (postID == null) {
      logger.error(ConstantMessage.MISSING_ARGUMENT.getMessage());
      throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
    }
    Post post = postRepository.getReferenceById(postID);
    if (post == null) {
      logger.warn(ConstantMessage.RESOURCE_NOT_FOUND.getMessage());
      throw new CustomException(ConstantMessage.RESOURCE_NOT_FOUND.getMessage());
    }
    return convertToPostResponse(post);
  }

  @Override
  public List<PostResponse> getAllPost() throws CustomException {
    return postRepository.findAll()
        .stream()
        .map(this::convertToPostResponse)
        .filter(Objects::nonNull)
        .toList();
  }

  @Override
  public List<PostResponse> getAllPostByTypeOfPost(TypeOfPosts typeOfPosts) throws CustomException {
    return postRepository.findAll()
        .stream()
        .map(this::convertToPostResponse)
        .filter(c -> c.getTypeOfPosts().toString().trim().toLowerCase()
            .contains(typeOfPosts.toString().trim().toLowerCase()))
        .toList();
  }

  @Override
  public List<PostResponse> getAllPostByHashtag(String hashtag) throws CustomException {
    return postRepository.findAll()
        .stream()
        .map(this::convertToPostResponse)
        .filter(c -> c.getHashtag().trim().toLowerCase().contains(hashtag.trim().toLowerCase()))
        .toList();
  }

  @Override
  public List<PostResponse> getAllPostInRange(Date startDate, Date endDate) throws CustomException {
    return postRepository.findAll()
        .stream()
        .map(this::convertToPostResponse)
        .filter(c -> c.getDate().after(startDate) && c.getDate().before(endDate))
        .toList();
  }

  @Override
  public Boolean deletePostByPostID(Integer postID) throws CustomException {
    if (postID == null) {
      throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
    }
    Post post = postRepository.getPostsByPostID(postID);
    if (post == null) {
      throw new CustomException(ConstantMessage.RESOURCE_NOT_FOUND.getMessage());
    }
    try {
      return postRepository.deleteByPostID(postID);
    } catch (Exception ex) {
      throw ex;
    }
  }

  @Override
  public PostResponse updatePost(PostRequest postRequest) throws CustomException {
    if (postRequest.getPostID().toString().isBlank() || postRequest.getPostID().toString()
        .isEmpty()) {
      logger.error(ConstantMessage.RESOURCE_NOT_FOUND.getMessage());
      throw new CustomException(ConstantMessage.RESOURCE_NOT_FOUND.getMessage());
    }
    if (!checkValidArguement(postRequest)) {
      logger.error(ConstantMessage.MISSING_ARGUMENT.getMessage());
      throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
    }
    User user = userService.getUserEntityByUsername(postRequest.getUserID());
    if (user == null) {
      logger.warn(ConstantMessage.CANNOT_FIND_USER_BY_USERNAME.getMessage());
      throw new CustomException(ConstantMessage.CANNOT_FIND_USER_BY_USERNAME.getMessage());
    }
    Post post = postRepository.getPostsByPostID(postRequest.getPostID());
    if (post == null) {
      logger.warn(ConstantMessage.RESOURCE_NOT_FOUND.getMessage());
      throw new CustomException(ConstantMessage.RESOURCE_NOT_FOUND.getMessage());
    }
    Post updatePost = Post
        .builder()
        .postID(post.getPostID())
//        .userPost(user)
        .typeOfPosts(post.getTypeOfPosts())
        .hashtag(post.getHashtag())
        .date(post.getDate())
        .status(post.getStatus())
//        .postImagesList(post.getPostImagesList())
//        .postReact(post.getPostReact())
//        .postComments(post.getPostComments())
        .build();
    postRepository.save(updatePost);
    logger.info("Update Post Information Successfully");
    return convertToPostResponse(updatePost);
  }

  private PostResponse convertToPostResponse(Post post) {
    return PostResponse
        .builder()
        .typeOfPosts(post.getTypeOfPosts())
        .postID(post.getPostID())
        .date(post.getDate())
//        .hashtag(post.getHashtag())
//        .userID(post.getUserPost().getUsername())
        .status(post.getStatus())
        .build();
  }
}
