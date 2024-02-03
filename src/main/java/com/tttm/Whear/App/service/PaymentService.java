package com.tttm.Whear.App.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.utils.request.PaymentRequest;
import com.tttm.Whear.App.utils.response.PaymentResponse;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface PaymentService {

  PaymentResponse createPayment(PaymentRequest paymentRequest)
      throws CustomException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException;
}
