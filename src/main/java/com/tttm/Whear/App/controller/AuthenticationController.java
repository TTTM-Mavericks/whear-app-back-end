package com.tttm.Whear.App.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tttm.Whear.App.constant.APIConstant;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.service.AuthenticationService;
import com.tttm.Whear.App.utils.request.AuthenticationRequest;
import com.tttm.Whear.App.utils.request.HistoryRequest;
import com.tttm.Whear.App.utils.request.UserRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(APIConstant.AuthenticationAPI.AUTHENTICATION)
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping(APIConstant.AuthenticationAPI.REGISTER)
    public ObjectNode register(@RequestBody UserRequest userRequest) throws CustomException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("success", 200);
            respon.put("message", "Register New Users Successfully");
            respon.set("data", objectMapper.valueToTree(authenticationService.register(userRequest)));
            return respon;
        } catch (Exception ex) {
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("error", -1);
            respon.put("message", ex.getMessage());
            respon.set("data", null);
            return respon;
        }
    }

    @PostMapping(APIConstant.AuthenticationAPI.LOGIN)
    public ObjectNode login(@RequestBody AuthenticationRequest authenticationRequest) throws CustomException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("success", 200);
            respon.put("message", "Login Successfully");
            respon.set("data", objectMapper.valueToTree(authenticationService.login(authenticationRequest)));
            return respon;
        } catch (Exception ex) {
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("error", -1);
            respon.put("message", ex.getMessage());
            respon.set("data", null);
            return respon;
        }
    }

    @PostMapping(APIConstant.AuthenticationAPI.REFRESH_TOKEN)
    public ObjectNode login( HttpServletRequest request, HttpServletResponse response) throws CustomException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ObjectNode respon = objectMapper.createObjectNode();
            respon.put("success", 200);
            respon.put("message", "Login Successfully");
            respon.set("data", objectMapper.valueToTree(authenticationService.refreshToken(request, response)));
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
