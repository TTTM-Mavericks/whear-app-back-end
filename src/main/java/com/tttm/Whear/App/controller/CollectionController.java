package com.tttm.Whear.App.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tttm.Whear.App.constant.APIConstant;
import com.tttm.Whear.App.constant.APIConstant.CollectionAPI;
import com.tttm.Whear.App.service.CollectionService;
import com.tttm.Whear.App.utils.response.CollectionResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CollectionAPI.COLLECTION)
public class CollectionController {
  @Autowired
  CollectionService collectionService;
  @GetMapping(CollectionAPI.GET_ALL_COLLECTION_BY_USER_ID)
  public ObjectNode getAllCollectionByUserID(@RequestParam(name = "userID") String userID){
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      List<CollectionResponse> collectionResponseList = collectionService.getCollectionsOfUser(userID);
      if(collectionResponseList == null || collectionResponseList.isEmpty()){
        ObjectNode respon = objectMapper.createObjectNode();
        respon.put("success", 200);
        respon.put("message", "Their is no collection for user: " + userID);
        respon.set("data", null);
        return respon;
      } else {
        ObjectNode respon = objectMapper.createObjectNode();
        respon.put("success", 200);
        respon.put("message", "Their are " + collectionResponseList.size() +" collections for user: " + userID);
        ArrayNode arrayNode = objectMapper.valueToTree(collectionResponseList);
        respon.set("data", arrayNode);
        return respon;
      }
    } catch (Exception ex){
      ObjectNode respon = objectMapper.createObjectNode();
      respon.put("error", -1);
      respon.put("message", ex.getMessage());
      respon.set("data", null);
      return respon;
    }
  }
}
