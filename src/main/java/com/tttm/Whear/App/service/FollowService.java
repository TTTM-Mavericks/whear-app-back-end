package com.tttm.Whear.App.service;

import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.utils.request.FollowRequest;
import com.tttm.Whear.App.utils.response.FollowResponse;
import com.tttm.Whear.App.utils.response.UserResponse;

import java.util.List;

public interface FollowService {
    FollowResponse userFollowAnotherUser(FollowRequest followRequest) throws CustomException;

    List<UserResponse> getAllFollowerUser(String username) throws CustomException;

    List<UserResponse> getAllFollowingUser(String username) throws CustomException;

}
