package com.tttm.Whear.App.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tttm.Whear.App.constant.APIConstant;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.service.FollowService;
import com.tttm.Whear.App.utils.request.FollowRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIConstant.FollowAPI.FOLLOW)
@RequiredArgsConstructor
public class FollowerController {

  private final FollowService followService;

  @PostMapping(APIConstant.FollowAPI.USER_FOLLOW_ANOTHER_USER)
  public ObjectNode userFollowAnotherUser(@RequestBody FollowRequest followRequest)
      throws CustomException {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      ObjectNode respon = objectMapper.createObjectNode();
      respon.put("success", 200);
      respon.put("message", "User Follow Another User Successfully");
      respon.set("data",
          objectMapper.valueToTree(followService.userFollowAnotherUser(followRequest)));
      return respon;
    } catch (Exception ex) {
      ObjectNode respon = objectMapper.createObjectNode();
      respon.put("error", -1);
      respon.put("message", ex.getMessage());
      respon.set("data", null);
      return respon;
    }
  }

  @GetMapping(APIConstant.FollowAPI.GET_ALL_FOLLOWER_USER)
  public ObjectNode getAllFollowerUser(@RequestParam("username") String username)
      throws CustomException {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      ObjectNode respon = objectMapper.createObjectNode();
      respon.put("success", 200);
      respon.put("message", "Get All Follower Users Successfully");
      respon.set("data", objectMapper.valueToTree(followService.getAllFollowerUser(username)));
      return respon;
    } catch (Exception ex) {
      ObjectNode respon = objectMapper.createObjectNode();
      respon.put("error", -1);
      respon.put("message", ex.getMessage());
      respon.set("data", null);
      return respon;
    }
  }

  @GetMapping(APIConstant.FollowAPI.GET_ALL_FOLLOWING_USER)
  public ObjectNode getAllFollowingUser(@RequestParam("username") String username)
      throws CustomException {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      ObjectNode respon = objectMapper.createObjectNode();
      respon.put("success", 200);
      respon.put("message", "Get All Following Users Successfully");
      respon.set("data", objectMapper.valueToTree(followService.getAllFollowingUser(username)));
      return respon;
    } catch (Exception ex) {
      ObjectNode respon = objectMapper.createObjectNode();
      respon.put("error", -1);
      respon.put("message", ex.getMessage());
      respon.set("data", null);
      return respon;
    }
  }
}
