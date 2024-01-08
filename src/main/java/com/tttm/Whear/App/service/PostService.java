package com.tttm.Whear.App.service;

import com.tttm.Whear.App.enums.TypeOfPosts;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.utils.request.PostRequest;
import com.tttm.Whear.App.utils.response.PostResponse;
import java.util.Date;
import java.util.List;

public interface PostService {

  public PostResponse createPost(PostRequest post) throws CustomException;

  public PostResponse getPostByPostID(Integer postID) throws CustomException;

  public List<PostResponse> getAllPost() throws CustomException;

  public List<PostResponse> getAllPostByTypeOfPost(TypeOfPosts typeOfPosts) throws CustomException;

  public List<PostResponse> getAllPostByHashtag(String hashtag) throws CustomException;

  public List<PostResponse> getAllPostInRange(Date startDate, Date endDate) throws CustomException;

  public Boolean deletePostByPostID(Integer postID) throws CustomException;

  public PostResponse updatePost(PostRequest postRequest) throws CustomException;
}
