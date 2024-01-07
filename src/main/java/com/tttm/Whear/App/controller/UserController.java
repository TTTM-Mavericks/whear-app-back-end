package com.tttm.Whear.App.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tttm.Whear.App.constant.APIConstant;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.service.UserService;
import com.tttm.Whear.App.utils.request.UserRequest;
import com.tttm.Whear.App.utils.response.CollectionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIConstant.UserAPI.USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping(APIConstant.UserAPI.CREATE_NEW_USER)
    public ObjectNode createNewUser(@RequestBody UserRequest userRequest) throws CustomException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("success", 200);
            respon.put("message", "Create new user Successfully");
            respon.set("data", objectMapper.valueToTree(userService.createNewUsers(userRequest)));
            return respon;
        } catch (Exception ex){
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("error", -1);
            respon.put("message", ex.getMessage());
            respon.set("data", null);
            return respon;
        }
    }

    @GetMapping(APIConstant.UserAPI.GET_ALL_USER)
    public ObjectNode getAllUser() throws CustomException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("success", 200);
            respon.put("message", "Get All Users Successfully");
            respon.set("data", objectMapper.valueToTree(userService.getAllUser()));
            return respon;
        } catch (Exception ex){
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("error", -1);
            respon.put("message", ex.getMessage());
            respon.set("data", null);
            return respon;
        }
    }

    @GetMapping(APIConstant.UserAPI.GET_USER_BY_USERNAME)
    public ObjectNode getUserByUsername(@RequestParam("username") String username) throws CustomException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("success", 200);
            respon.put("message", "Get User by Username Successfully");
            respon.set("data", objectMapper.valueToTree(userService.getUserbyUsername(username)));
            return respon;
        } catch (Exception ex){
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("error", -1);
            respon.put("message", ex.getMessage());
            respon.set("data", null);
            return respon;
        }
    }

    @PutMapping(APIConstant.UserAPI.UPDATE_USER_BY_USERNAME)
    public ObjectNode updateUserByUsername(@RequestBody UserRequest userRequest) throws CustomException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("success", 200);
            respon.put("message", "Update User Information Successfully");
            respon.set("data", objectMapper.valueToTree(userService.updateUserByUsername(userRequest)));
            return respon;
        } catch (Exception ex){
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("error", -1);
            respon.put("message", ex.getMessage());
            respon.set("data", null);
            return respon;
        }
    }

    @PutMapping(APIConstant.UserAPI.UPDATE_STATUS_USER)
    public ObjectNode updateStatusUser(@RequestParam("username") String username) throws CustomException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("success", 200);
            respon.put("message", "Change Status User Successfully");
            respon.set("data", objectMapper.valueToTree(userService.updateStatusUser(username)));
            return respon;
        } catch (Exception ex){
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("error", -1);
            respon.put("message", ex.getMessage());
            respon.set("data", null);
            return respon;
        }
    }

    @GetMapping(APIConstant.UserAPI.GET_USER_BY_USERNAME_AND_PASSWORD)
    public ObjectNode getUserByUsername(@RequestParam("username") String username,
                                        @RequestParam("password") String password) throws CustomException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("success", 200);
            respon.put("message", "Get User by Username and Password Successfully");
            respon.set("data", objectMapper.valueToTree(userService.getUserByUsernameAndPassword(username, password)));
            return respon;
        } catch (Exception ex){
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("error", -1);
            respon.put("message", ex.getMessage());
            respon.set("data", null);
            return respon;
        }
    }
}
