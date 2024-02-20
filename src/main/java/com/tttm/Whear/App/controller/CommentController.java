package com.tttm.Whear.App.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tttm.Whear.App.constant.APIConstant.CommentAPI;
import com.tttm.Whear.App.constant.ConstantMessage;
import com.tttm.Whear.App.service.CommentService;
import com.tttm.Whear.App.utils.request.CommentsRequest;
import com.tttm.Whear.App.utils.response.CommentsResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(CommentAPI.COMMENT)
public class CommentController {

  private final CommentService commentService;

  @PostMapping(CommentAPI.CREATE_COMMENT)
  public ObjectNode createComment(@RequestBody CommentsRequest comment) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      CommentsResponse commentsResponse = commentService.createComment(comment);
      if (commentsResponse == null) {
        ObjectNode respon = objectMapper.createObjectNode();
        respon.put("fail", 200);
        respon.put("message", ConstantMessage.CREATE_FAIL.getMessage());
        respon.set("data", null);
        return respon;
      } else {
        ObjectNode respon = objectMapper.createObjectNode();
        respon.put("success", 200);
        respon.put("message", ConstantMessage.CREATE_SUCCESS.getMessage());
        respon.set("data", objectMapper.valueToTree(commentsResponse));
        return respon;
      }
    } catch (Exception ex) {
      ObjectNode respon = objectMapper.createObjectNode();
      respon.put("error", -1);
      respon.put("message", ex.getMessage());
      respon.set("data", null);
      return respon;
    }
  }

  @GetMapping(CommentAPI.GET_ALL_COMMENT_OF_A_POST)
  public ObjectNode getAllComment(@RequestParam(name = "postID") Integer postID) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      List<CommentsResponse> allComment = commentService.getAllComment(postID);
      if (allComment == null) {
        ObjectNode respon = objectMapper.createObjectNode();
        respon.put("fail", 200);
        respon.put("message", "NO DATA");
        respon.set("data", objectMapper.valueToTree(new ArrayList<>()));
        return respon;
      } else {
        ObjectNode respon = objectMapper.createObjectNode();
        respon.put("success", 200);
        respon.put("message", "Get data successfully!");
        respon.set("data", objectMapper.valueToTree(allComment));
        return respon;
      }
    } catch (Exception ex) {
      ObjectNode respon = objectMapper.createObjectNode();
      respon.put("error", -1);
      respon.put("message", ex.getMessage());
      respon.set("data", null);
      return respon;
    }
  }

  @DeleteMapping(CommentAPI.DELETE_BY_COMMENT_ID)
  public ObjectNode deleteComment(@RequestParam(name = "postID") Integer postID,
      @RequestParam(name = "commentID") Integer commentID) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      commentService.deleteComment(postID, commentID);
      ObjectNode respon = objectMapper.createObjectNode();
      respon.put("success", 200);
      respon.put("message", "Delete Successfully");
      respon.set("data", null);
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
