package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.constant.ConstantMessage;
import com.tttm.Whear.App.entity.Hashtag;
import com.tttm.Whear.App.entity.Post;
import com.tttm.Whear.App.entity.PostHashtag;
import com.tttm.Whear.App.entity.User;
import com.tttm.Whear.App.enums.StatusGeneral;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.repository.PostHashtagRepository;
import com.tttm.Whear.App.repository.PostRepository;
import com.tttm.Whear.App.service.HashtagService;
import com.tttm.Whear.App.service.PostService;
import com.tttm.Whear.App.service.UserService;
import com.tttm.Whear.App.utils.request.PostRequest;
import com.tttm.Whear.App.utils.response.PostResponse;
import java.util.ArrayList;
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
  private final HashtagService hashtagService;
  private final PostHashtagRepository postHashtagRepository;

  private boolean checkValidArguement(PostRequest postRequest) {
    return postRequest.getPostID() != null && postRequest.getUserID() != null &&
        !postRequest.getUserID().isEmpty() && !postRequest.getUserID().isBlank() &&
        !postRequest.getPostID().toString().isEmpty() && !postRequest.getPostID().toString()
        .isBlank();
  }

  @Override
//  @Caching(
//          evict = @CacheEvict(cacheNames = "posts", allEntries = true),
//          cacheable = @Cacheable(cacheNames = "post", key = "#postRequest.postID", condition = "#postRequest.postID > 0", unless = "#result == null")
//  )
  public PostResponse createPost(PostRequest postRequest) throws CustomException {
    if (postRequest.getUserID().isEmpty() || postRequest.getUserID().isBlank()) {
      logger.error(ConstantMessage.MISSING_ARGUMENT.getMessage());
      throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
    }
    User user = userService.getUserEntityByUserID(postRequest.getUserID());
    if (user == null) {
      logger.error(ConstantMessage.CANNOT_FIND_USER_BY_USERID.getMessage());
      throw new CustomException(ConstantMessage.CANNOT_FIND_USER_BY_USERID.getMessage());
    }

    List<Hashtag> hashtagList = null;
    List<String> hashtags = postRequest.getHashtag();
    if (hashtags != null && !hashtags.isEmpty() && hashtags.size() > 0) {
      for (String ht : hashtags) {
        Hashtag finded = hashtagService.findByName(ht);
        Hashtag importedHastag = null;
        if (finded == null) {
          importedHastag = hashtagService.createHashtag(ht);
        } else {
          importedHastag = finded;
        }

        if (hashtagList == null) {
          hashtagList = new ArrayList<>();
        }
        hashtagList.add(importedHastag);
      }
    }

    Post post = Post
        .builder()
        .postID(Integer.valueOf(String.valueOf(postRepository.count() + 1)))
        .userID(postRequest.getUserID())
        .typeOfPosts(postRequest.getTypeOfPosts() != null ? postRequest.getTypeOfPosts() : null)
        .status(StatusGeneral.ACTIVE)
        .date(new Date())
        .build();
    post = postRepository.save(post);

    for (Hashtag ht : hashtagList) {
      postHashtagRepository.insertPostHashtag(ht.getHashtagID(), post.getPostID());
    }

    logger.info(ConstantMessage.CREATE_SUCCESS.getMessage());
    return convertToPostResponse(post);
  }

  @Override
//  @Cacheable(cacheNames = "post", key = "#postID", condition = "#postID > 0", unless = "#result == null")
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
//  @Cacheable(cacheNames = "posts")
  public List<PostResponse> getAllPost() {
    return postRepository.findAll()
        .stream()
        .map(this::convertToPostResponse)
        .filter(Objects::nonNull)
        .toList();
  }

  @Override
  public List<PostResponse> getAllPostByTypeOfPost(String typeOfPosts) throws CustomException {
    return postRepository.findAll()
        .stream()
        .map(this::convertToPostResponse)
        .filter(c -> c.getTypeOfPosts().toString().trim().toLowerCase()
            .contains(typeOfPosts.trim().toLowerCase()))
        .toList();
  }

  @Override
  public List<PostResponse> getAllPostByHashtag(String hashtag) throws CustomException {
    List<PostResponse> postList = null;
    List<PostHashtag> postHashtagList = postHashtagRepository.findAll()
        .stream()
        .filter(postHashtag -> {
          return hashtagService.getByHashtagID(postHashtag.getPostHashtagKey().getHashtagID())
              .getHashtag()
              .equals(hashtag);
        })
        .toList();
    for (PostHashtag pht : postHashtagList) {
      if (postList == null) {
        postList = new ArrayList<>();
      }
      postList.add(convertToPostResponse(
          postRepository.getPostsByPostID(
              pht.getPostHashtagKey()
                  .getPostID()
          )
      ));
    }
    return postList;
  }

  @Override
  public List<PostResponse> getAllPostInRange(Date startDate, Date endDate) throws CustomException {
    return postRepository.findAll()
        .stream()
        .filter(c -> c.getDate().after(startDate) && c.getDate().before(endDate))
        .map(this::convertToPostResponse)
        .toList();
  }

  @Override
//  @Caching(
//      evict = {
//          @CacheEvict(cacheNames = "posts", allEntries = true),
//          @CacheEvict(cacheNames = "post", key = "#postID")
//      }
//  )
  public Boolean deletePostByPostID(Integer postID) throws CustomException {
    if (postID == null) {
      throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
    }
    Post post = postRepository.getPostsByPostID(postID);
    if (post == null) {
      throw new CustomException(ConstantMessage.RESOURCE_NOT_FOUND.getMessage());
    }

    List<PostHashtag> postHashtagList = postHashtagRepository.findAll()
        .stream()
        .filter(postHashtag -> {
          return postRepository.getPostsByPostID(postHashtag.getPostHashtagKey().getPostID())
              != null;
        })
        .toList();
    for (PostHashtag pth : postHashtagList) {
      postHashtagRepository.deletePostHashtag(
          pth.getPostHashtagKey().getHashtagID(),
          pth.getPostHashtagKey().getPostID()
      );
    }
    try {
      return postRepository.deleteByPostID(postID) > 0;
    } catch (Exception ex) {
      throw ex;
    }
  }

  @Override
//  @Caching(
//      evict = @CacheEvict(cacheNames = "posts", allEntries = true),
//      put = @CachePut(cacheNames = "post", key = "#postRequest.postID", unless = "#result == null")
//  )
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
    User user = userService.getUserEntityByUserID(postRequest.getUserID());
    if (user == null) {
      logger.warn(ConstantMessage.CANNOT_FIND_USER_BY_USERID.getMessage());
      throw new CustomException(ConstantMessage.CANNOT_FIND_USER_BY_USERID.getMessage());
    }
    Post post = postRepository.getPostsByPostID(postRequest.getPostID());
    if (post == null) {
      logger.warn(ConstantMessage.RESOURCE_NOT_FOUND.getMessage());
      throw new CustomException(ConstantMessage.RESOURCE_NOT_FOUND.getMessage());
    }

    List<Hashtag> hashtagList = null;
    List<String> hashtags = postRequest.getHashtag();
    if (hashtags != null && !hashtags.isEmpty() && hashtags.size() > 0) {
      for (String ht : hashtags) {
        Hashtag finded = hashtagService.findByName(ht);
        Hashtag importedHastag = null;
        if (finded == null) {
          importedHastag = hashtagService.createHashtag(ht);
        } else {
          importedHastag = finded;
        }

        if (hashtagList == null) {
          hashtagList = new ArrayList<>();
        }
        hashtagList.add(importedHastag);
      }
    }

    Post updatePost = Post
        .builder()
        .postID(post.getPostID())
        .userID(post.getUserID())
        .typeOfPosts(postRequest.getTypeOfPosts() != null ? postRequest.getTypeOfPosts()
            : post.getTypeOfPosts())
        .date(postRequest.getDate() != null ? postRequest.getDate() : post.getDate())
        .status(postRequest.getStatus() != null ? postRequest.getStatus() : post.getStatus())
        .build();
    postRepository.save(updatePost);
    logger.info("Update Post Information Successfully");
    return convertToPostResponse(updatePost);
  }

  @Override
  public Post getPostEntityByPostID(Integer postID) throws CustomException {
    if (postID == null) {
      logger.error(ConstantMessage.MISSING_ARGUMENT.getMessage());
      throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
    }
    Post post = postRepository.getReferenceById(postID);
    if (post == null) {
      logger.warn(ConstantMessage.RESOURCE_NOT_FOUND.getMessage());
      throw new CustomException(ConstantMessage.RESOURCE_NOT_FOUND.getMessage());
    }
    return post;
  }

  @Override
  public List<PostResponse> getAllPostForUser(String userID) throws CustomException {
    if (userID == null || userID.isEmpty() || userID.isBlank()) {
      throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
    }
    User u = userService.getUserEntityByUserID(userID);
    if (u == null) {
      throw new CustomException(ConstantMessage.CANNOT_FIND_USER_BY_USERID.getMessage());
    }

    List<PostResponse> responseList = null;

    List<Post> postList = postRepository.getAllPostForUser(userID);
    if (postList != null && !postList.isEmpty() && postList.size() > 0) {
      for (Post p : postList) {
        if (responseList == null) {
          responseList = new ArrayList<>();
        }
        responseList.add(convertToPostResponse(p));
      }
    }

    return responseList;
  }

  @Override
  public List<PostResponse> getAllPostOfUser(String userID) throws CustomException {
    if (userID == null || userID.isEmpty() || userID.isBlank()) {
      throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
    }
    User u = userService.getUserEntityByUserID(userID);
    if (u == null) {
      throw new CustomException(ConstantMessage.CANNOT_FIND_USER_BY_USERID.getMessage());
    }

    List<PostResponse> responseList = null;

    List<Post> postList = postRepository.getAllByUserID(userID);
    if (postList != null && !postList.isEmpty() && postList.size() > 0) {
      for (Post p : postList) {
        if (responseList == null) {
          responseList = new ArrayList<>();
        }
        responseList.add(convertToPostResponse(p));
      }
    }
    return responseList;
  }

  private PostResponse convertToPostResponse(Post post) {
    return PostResponse
        .builder()
        .typeOfPosts(post.getTypeOfPosts())
        .postID(post.getPostID())
        .date(post.getDate().toString())
        .hashtag(
            hashtagService.getAllHashtagOfPost(post.getPostID())
        )
        .userID(post.getUserID())
        .status(post.getStatus())
        .build();
  }
}
