package com.tttm.Whear.App.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tttm.Whear.App.constant.APIConstant;
import com.tttm.Whear.App.enums.ENotificationAction;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.service.BrandService;
import com.tttm.Whear.App.utils.request.BrandRequestDto;
import com.tttm.Whear.App.utils.request.ClothesRequest;
import com.tttm.Whear.App.utils.request.HistoryRequest;
import com.tttm.Whear.App.utils.request.NotificationRequest;
import com.tttm.Whear.App.utils.response.ClothesResponse;
import com.tttm.Whear.App.utils.response.NotificationResponse;
import com.tttm.Whear.App.utils.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(APIConstant.BrandAPI.BRAND)
public class BrandController {
    private final BrandService brandService;

    @PostMapping(APIConstant.BrandAPI.CREATE_NEW_BRAND)
    public ObjectNode createNewBrand(@RequestBody BrandRequestDto brandRequestDto) throws CustomException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("success", 200);
            respon.put("message", "Create History Items Successfully");
            respon.set("data", objectMapper.valueToTree(brandService.createNewBrand(brandRequestDto)));
            return respon;
        } catch (Exception ex) {
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("error", -1);
            respon.put("message", ex.getMessage());
            respon.set("data", null);
            return respon;
        }
    }

    @GetMapping(APIConstant.BrandAPI.GET_HOT_BRAND)
    public ObjectNode getListHotBrand() throws CustomException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("success", 200);
            respon.put("message", "Get List Hot Brand Successfully");
            respon.set("data", objectMapper.valueToTree(brandService.getListHotBrand()));
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
