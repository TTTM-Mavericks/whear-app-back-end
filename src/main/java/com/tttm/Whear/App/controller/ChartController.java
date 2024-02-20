package com.tttm.Whear.App.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tttm.Whear.App.constant.APIConstant.ChartAPI;
import com.tttm.Whear.App.entity.User;
import com.tttm.Whear.App.enums.Language;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.service.UserService;
import com.tttm.Whear.App.utils.response.LanguageChartResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(ChartAPI.CHART)
public class ChartController {

  private final UserService userService;

  @GetMapping(ChartAPI.LANGUAGE_CHART)
  public ObjectNode languageChart() throws CustomException {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      List<User> userList = userService.getAllUserEntity();
      List<LanguageChartResponse> responseList = new ArrayList<>();
      LanguageChartResponse vi = LanguageChartResponse
          .builder()
          .language(Language.VIETNAM.name())
          .quantity(0)
          .build();
      LanguageChartResponse en = LanguageChartResponse
          .builder()
          .language(Language.ENGLISH.name())
          .quantity(0)
          .build();
      for (User user : userList) {
        if (user.getLanguage().equals(Language.VIETNAM)) {
          vi.setQuantity(vi.getQuantity() + 1);
        } else {
          if (user.getLanguage().equals(Language.ENGLISH)) {
            en.setQuantity(en.getQuantity() + 1);
          }
        }
      }
      responseList.add(vi);
      responseList.add(en);
      ObjectNode respon = objectMapper.createObjectNode();
      respon.put("success", 200);
      respon.put("message", "Language chart");
      respon.set("data", objectMapper.valueToTree(responseList));
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
