package com.tttm.Whear.App.utils.request;

import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import lombok.Data;

@Data
public class PaymentRequest {

  private Integer orderCode;  // paymentID
  private Integer amount;
  private String description;
  private String buyerName;
  private String buyerEmail;
  private String buyerPhone;
  private String items;
  private String cancelUrl;
  private String returnUrl;
  private Integer expiredAt;
  private String signature;
  private String customerID;
  private String status;
}
