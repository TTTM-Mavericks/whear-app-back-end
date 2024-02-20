package com.tttm.Whear.App.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tttm.Whear.App.constant.APIConstant.PaymentAPI;
import com.tttm.Whear.App.service.PaymentService;
import com.tttm.Whear.App.utils.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(PaymentAPI.PAYMENT)
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping(PaymentAPI.CREATE_PAYMENT)
  public ObjectNode createPayment(@RequestBody PaymentRequest paymentRequest) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      ObjectNode respon = objectMapper.createObjectNode();
      respon.put("success", 200);
      respon.put("message", "Create payment Successfully");
      respon.set("data",
          objectMapper.valueToTree(paymentService.createPayment(paymentRequest)));
      return respon;
    } catch (Exception ex) {
      ObjectNode respon = objectMapper.createObjectNode();
      respon.put("error", -1);
      respon.put("message", ex.getMessage());
      respon.set("data", null);
      return respon;
    }
  }

  @GetMapping(PaymentAPI.GET_PAYMENT_BY_ID)
  public ObjectNode getPaymentByID(@RequestBody PaymentRequest paymentRequest) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      ObjectNode respon = objectMapper.createObjectNode();
      respon.put("success", 200);
      respon.put("message", "Create payment Successfully");
      respon.set("data",
          objectMapper.valueToTree(
              paymentService.getPaymentInfor(paymentRequest.getOrderCode().toString())));
      return respon;
    } catch (Exception ex) {
      ObjectNode respon = objectMapper.createObjectNode();
      respon.put("error", -1);
      respon.put("message", ex.getMessage());
      respon.set("data", null);
      return respon;
    }
  }

//  @GetMapping(PaymentAPI.GET_ALL_PAYMENT)
//  public ObjectNode getAllPayment() {
//    ObjectMapper objectMapper = new ObjectMapper();
//    try {
//      ObjectNode respon = objectMapper.createObjectNode();
//      respon.put("success", 200);
//      respon.put("message", "Get All Payment Successfully");
//      respon.set("data",
//          objectMapper.valueToTree(
//              paymentService.getAllPayment()
//          )
//      );
//      return respon;
//    } catch (Exception ex) {
//      ObjectNode respon = objectMapper.createObjectNode();
//      respon.put("error", -1);
//      respon.put("message", ex.getMessage());
//      respon.set("data", null);
//      return respon;
//    }
//  }
}
