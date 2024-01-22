package com.tttm.Whear.App.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tttm.Whear.App.constant.APIConstant;
import com.tttm.Whear.App.enums.ENotificationAction;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.service.FollowService;
import com.tttm.Whear.App.service.NotificationService;
import com.tttm.Whear.App.service.UserService;
import com.tttm.Whear.App.utils.request.FollowRequest;
import com.tttm.Whear.App.utils.request.NotificationRequest;
import com.tttm.Whear.App.utils.response.FollowResponse;
import com.tttm.Whear.App.utils.response.FollowingResponse;
import com.tttm.Whear.App.utils.response.UserResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
  private final NotificationService notificationService;
  private final UserService userService;

  @PostMapping(APIConstant.FollowAPI.USER_FOLLOW_OR_UNFOLLOW_ANOTHER_USER)
  public ObjectNode userFollowOrUnfollowAnotherUser(@RequestBody FollowRequest followRequest)
      throws CustomException {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      ObjectNode respon = objectMapper.createObjectNode();
      FollowResponse followResponse = followService.userFollowAnotherUser(followRequest);
      if (followResponse.getFollowerUser() != null && followResponse.getFollowingUser() != null) {
        respon.put("success", 200);
        respon.put("message",
            "User " + followRequest.getBaseUserID() + " Follow " + followRequest.getTargetUserID()
                + " Successfully");
        respon.set("data", objectMapper.valueToTree(followResponse));
        NotificationRequest notiRequest = NotificationRequest.builder()
            .action(ENotificationAction.FOLLOW.name())
            .actionID(Integer.parseInt(followRequest.getBaseUserID()))
            .baseUserID(followRequest.getBaseUserID())
            .targetUserID(followRequest.getTargetUserID())
            .dateTime(LocalDateTime.now())
            .build();
        notificationService.sendNotification(notiRequest);
      } else {
        respon.put("success", 200);
        respon.put("message",
            "User " + followRequest.getBaseUserID() + " Unfollow " + followRequest.getTargetUserID()
                + " Successfully");
        respon.set("data", objectMapper.valueToTree(followResponse));
      }
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
  public ObjectNode getAllFollowerUser(@RequestParam("userid") String userid)
      throws CustomException {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      ObjectNode respon = objectMapper.createObjectNode();
      respon.put("success", 200);
      respon.put("message", "Get All Follower Users Successfully");
      respon.set("data", objectMapper.valueToTree(followService.getAllFollowerUser(userid)));
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
  public ObjectNode getAllFollowingUser(@RequestParam("base_userid") String base_userid,
      @RequestParam("userid") String userid)
      throws CustomException {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      ObjectNode respon = objectMapper.createObjectNode();
      respon.put("success", 200);
      respon.put("message", "Get All Following Users Successfully");

      List<UserResponse> userResponseList = followService.getAllFollowingUser(userid);
      List<FollowingResponse> finalResponse = new ArrayList<>();
      for (UserResponse user : userResponseList) {
        if (!base_userid.equals(user.getUserID())) {
          finalResponse.add(
              FollowingResponse.builder()
                  .userResponse(user)
                  .followed(followService.checkContain(base_userid, user.getUserID()) != null)
                  .build()
          );
        }
      }
      respon.set("data", objectMapper.valueToTree(finalResponse));
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
