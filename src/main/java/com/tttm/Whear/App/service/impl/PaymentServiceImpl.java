package com.tttm.Whear.App.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tttm.Whear.App.constant.ConstantMessage;
import com.tttm.Whear.App.entity.Customer;
import com.tttm.Whear.App.entity.SubRole;
import com.tttm.Whear.App.enums.ESubRole;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.service.CustomerService;
import com.tttm.Whear.App.service.PaymentService;
import com.tttm.Whear.App.service.SubroleService;
import com.tttm.Whear.App.utils.request.PaymentRequest;
import com.tttm.Whear.App.utils.response.PaymentData;
import com.tttm.Whear.App.utils.response.PaymentResponse;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

  @Value("${PAYOS_CREATE_PAYMENT_LINK_URL}")
  private String createPaymentLinkUrl;
  @Value("${PAYOS_CLIENT_ID}")
  private String clientId;
  @Value("${PAYOS_API_KEY}")
  private String apiKey;
  @Value("${PAYOS_CHECKSUM_KEY}")
  private String checksumKey;

  private final CustomerService customerService;
  private final SubroleService subroleService;

  @Override
  public PaymentResponse createPayment(PaymentRequest paymentRequest)
      throws CustomException, NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    String customerID = paymentRequest.getCustomerID();
    if (customerID == null) {
      throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
    }
    Customer customer = customerService.getCustomerByID(customerID);
    if (customer == null) {
      throw new CustomException(
          ConstantMessage.CANNOT_FIND_USER_BY_USERID.getMessage() + " " + customerID);
    }

    String description = paymentRequest.getDescription().trim();
    String buyerName = paymentRequest.getBuyerName().trim();
    String buyerEmail = paymentRequest.getBuyerEmail().trim();
    String buyerPhone = paymentRequest.getBuyerPhone().trim();

    if (buyerName.isEmpty() || buyerName.isBlank()) {
      buyerName = customer.getUser().getUsername();
    }

    if (buyerEmail.isBlank() || buyerEmail.isEmpty()) {
      buyerEmail = customer.getUser().getEmail();
    }

    if (buyerPhone.isEmpty() || buyerPhone.isBlank()) {
      buyerPhone = customer.getUser().getPhone();
    }

    String items = paymentRequest.getItems();
    if (items == null || items.isBlank() || items.isEmpty()) {
      throw new CustomException(ConstantMessage.MISSING_ARGUMENT.getMessage());
    }
    SubRole subRole = subroleService.getSubroleBySubroleName(ESubRole.valueOf(items));
    if (subRole == null) {
      throw new CustomException(ConstantMessage.RESOURCE_NOT_FOUND + " for SUBROLE: " + items);
    }

    Integer amount = subRole.getPrice();

    String currentTimeString = String.valueOf(String.valueOf(new Date().getTime()));
    Integer orderCode = Integer.parseInt(
        currentTimeString.substring(currentTimeString.length() - 6));

    String status = "WAITING";

    paymentRequest.setBuyerEmail(buyerEmail);
    paymentRequest.setBuyerName(buyerName);
    paymentRequest.setBuyerPhone(buyerPhone);
    paymentRequest.setAmount(amount);
    paymentRequest.setOrderCode(orderCode);

    String bodyToSignature = createSignatureOfPaymentRequest(paymentRequest, checksumKey);
    paymentRequest.setSignature(bodyToSignature);

    // Tạo header
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("x-client-id", clientId);
    headers.set("x-api-key", apiKey);
    // Gửi yêu cầu POST
    WebClient client = WebClient.create();
    Mono<String> response = client.post()
        .uri(createPaymentLinkUrl)
        .headers(httpHeaders -> httpHeaders.putAll(headers))
        .body(BodyInserters.fromValue(paymentRequest))
        .retrieve()
        .bodyToMono(String.class);
    String responseBody = response.block();
    JsonNode res = objectMapper.readTree(responseBody);
    System.out.println(res);
    if (!Objects.equals(res.get("code").asText(), "00")) {
      throw new CustomException("Fail");
    }
    String checkoutUrl = res.get("data").get("checkoutUrl").asText();

    //Kiểm tra dữ liệu có đúng không
    String paymentLinkResSignature = createSignatureFromObj(res.get("data"), checksumKey);
    System.out.println(paymentLinkResSignature);
    if (!paymentLinkResSignature.equals(res.get("signature").asText())) {
//      orderRepository.deleteOrderByOrderID(newestOrder.getId());
      throw new CustomException("Signature is not compatible");
    }

    PaymentResponse paymentResponse = PaymentResponse
        .builder()
        .code(res.get("code").asText())
        .desc("Success - Thành công")
        .data(PaymentData
            .builder()
            .bin("")
            .accountNumber("")
            .accountName("")
            .amount(0)
            .description("")
            .orderCode(orderCode)
            .currency("")
            .paymentLinkId("")
            .status("PENDING")
            .checkoutUrl("")
            .qrCode("")
            .build()
        )
        .signature(paymentRequest.getSignature())
        .build();

    return paymentResponse;
  }

  private static String convertObjToQueryStr(JsonNode object) {
    StringBuilder stringBuilder = new StringBuilder();
    ObjectMapper objectMapper = new ObjectMapper();

    object.fields().forEachRemaining(entry -> {
      String key = entry.getKey();
      JsonNode value = entry.getValue();
      String valueAsString = value.isTextual() ? value.asText() : value.toString();

      if (!stringBuilder.isEmpty()) {
        stringBuilder.append('&');
      }
      stringBuilder.append(key).append('=').append(valueAsString);
    });

    return stringBuilder.toString();
  }

  private static JsonNode sortObjDataByKey(JsonNode object) {
    if (!object.isObject()) {
      return object;
    }

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectNode orderedObject = objectMapper.createObjectNode();

    Iterator<Entry<String, JsonNode>> fieldsIterator = object.fields();
    TreeMap<String, JsonNode> sortedMap = new TreeMap<>();

    while (fieldsIterator.hasNext()) {
      Entry<String, JsonNode> field = fieldsIterator.next();
      sortedMap.put(field.getKey(), field.getValue());
    }

    sortedMap.forEach(orderedObject::set);

    return orderedObject;
  }

  private static String generateHmacSHA256(String dataStr, String key)
      throws NoSuchAlgorithmException, InvalidKeyException {
    Mac sha256Hmac = Mac.getInstance("HmacSHA256");
    SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
    sha256Hmac.init(secretKey);
    byte[] hmacBytes = sha256Hmac.doFinal(dataStr.getBytes(StandardCharsets.UTF_8));

    // Chuyển byte array sang chuỗi hex
    StringBuilder hexStringBuilder = new StringBuilder();
    for (byte b : hmacBytes) {
      hexStringBuilder.append(String.format("%02x", b));
    }
    return hexStringBuilder.toString();
  }

  public static String createSignatureFromObj(JsonNode data, String key)
      throws NoSuchAlgorithmException, InvalidKeyException {
    JsonNode sortedDataByKey = sortObjDataByKey(data);
    String dataQueryStr = convertObjToQueryStr(sortedDataByKey);
    return generateHmacSHA256(dataQueryStr, key);
  }

  public static String createSignatureOfPaymentRequest(PaymentRequest data, String key)
      throws NoSuchAlgorithmException, InvalidKeyException {
    int amount = data.getAmount();
    String cancelUrl = data.getCancelUrl();
    String description = data.getDescription();
    String orderCode = Integer.toString(data.getOrderCode());
    String returnUrl = data.getReturnUrl();
    String dataStr = "amount=" + amount + "&cancelUrl=" + cancelUrl + "&description=" + description
        + "&orderCode=" + orderCode + "&returnUrl=" + returnUrl;
//    String dataStr = "amount=" + amount + "&description=" + description
//        + "&orderCode=" + orderCode;
    // Sử dụng HMAC-SHA-256 để tính toán chữ ký
    return generateHmacSHA256(dataStr, key);
  }
}
