package com.tttm.Whear.App.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tttm.Whear.App.constant.APIConstant.ClothesAPI;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.service.ClothesService;
import com.tttm.Whear.App.utils.request.ClothesRequest;
import com.tttm.Whear.App.utils.response.ClothesResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(ClothesAPI.CLOTHES)
public class ClothesController {

  private final ClothesService clothesService;

  @GetMapping(ClothesAPI.GET_ALL_CLOTHES)
  public ObjectNode getAllClothes() {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      List<ClothesResponse> responseList = clothesService.getAllClothes();
      if (responseList == null || responseList.isEmpty()) {
        ObjectNode respon = objectMapper.createObjectNode();
        respon.put("success", 200);
        respon.put("message", "Their is no clothes!");
        respon.set("data", null);
        return respon;
      } else {
        ObjectNode respon = objectMapper.createObjectNode();
        respon.put("success", 200);
        respon.put("message", "Their are " + responseList.size() + " clothes");
        ArrayNode arrayNode = objectMapper.valueToTree(responseList);
        respon.set("data", arrayNode);
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

  @GetMapping(ClothesAPI.CREATE_CLOTHES)
  public ObjectNode createClothes(@RequestBody ClothesRequest clothesRequest) throws CustomException{
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      ClothesResponse response = clothesService.createClothes(clothesRequest);
      if (response == null) {
        ObjectNode respon = objectMapper.createObjectNode();
        respon.put("success", 200);
        respon.put("message", "Create Fail!");
        respon.set("data", null);
        return respon;
      } else {
        ObjectNode respon = objectMapper.createObjectNode();
        respon.put("success", 200);
        respon.put("message", "Create Clothes Successfully!");
        JsonNode arrayNode = objectMapper.valueToTree(response);
        respon.set("data", arrayNode);
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

  @GetMapping(ClothesAPI.GET_CLOTHES_BY_ID)
  public ObjectNode getClothesByID(@RequestParam(name = "clothes_id") Integer clothesID)
      throws CustomException {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      ClothesResponse responseList = clothesService.getClothesByID(clothesID);
      if (responseList == null) {
        ObjectNode respon = objectMapper.createObjectNode();
        respon.put("success", 200);
        respon.put("message", "Clothes not found!");
        respon.set("data", null);
        return respon;
      } else {
        ObjectNode respon = objectMapper.createObjectNode();
        respon.put("success", 200);
        respon.put("message", "Get Clothes Successfully!");
        JsonNode arrayNode = objectMapper.valueToTree(responseList);
        respon.set("data", arrayNode);
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
}
