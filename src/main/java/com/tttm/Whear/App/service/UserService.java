package com.tttm.Whear.App.service;

import com.tttm.Whear.App.entity.User;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.utils.request.UserRequest;
import com.tttm.Whear.App.utils.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createNewUsers(UserRequest userRequest) throws CustomException;
    UserResponse getUserbyUsername(String username) throws CustomException;
    List<UserResponse> getAllUser() throws CustomException;
    UserResponse getUserByUsernameAndPassword(String username, String password) throws CustomException;
    UserResponse updateUserByUsername(UserRequest userRequest) throws CustomException;
    UserResponse updateStatusUser(String username) throws CustomException;
    User getUserEntityByUsername(String username) throws CustomException;
}
