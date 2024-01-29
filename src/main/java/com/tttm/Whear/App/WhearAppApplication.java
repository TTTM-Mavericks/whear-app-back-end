package com.tttm.Whear.App;

import com.tttm.Whear.App.entity.SubRole;
import com.tttm.Whear.App.enums.ESubRole;
import com.tttm.Whear.App.repository.SubRoleRepository;
import com.tttm.Whear.App.utils.request.NotificationRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
@SpringBootApplication
@EnableJpaAuditing()
@EnableCaching
public class WhearAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(WhearAppApplication.class, args);

  }

  @MessageMapping("/noti.sendMessage")
  @SendToUser("/topic/public")
  public NotificationRequest sendMessage(@Payload NotificationRequest chatMessage) {
    return chatMessage;
  }

  @MessageMapping("/noti.addUser")
  @SendToUser("/topic/public")
  public NotificationRequest addUser(@Payload NotificationRequest chatMessage,
      SimpMessageHeaderAccessor headerAccessor) {
    headerAccessor.getSessionAttributes().put("username", chatMessage.getTargetUserID());
    return chatMessage;
  }

  @Bean
  public CommandLineRunner commandLineRunner(SubRoleRepository subRoleRepository) {
    return args -> {
      if (subRoleRepository.findAll().size() == 0) {
        SubRole lv1 = SubRole.builder()
            .subRoleName(ESubRole.LV1)
            .numberOfCollection(2)
            .numberOfClothes(2)
            .build();
        SubRole lv2 = SubRole.builder()
            .subRoleName(ESubRole.LV2)
            .numberOfCollection(3)
            .numberOfClothes(3)
            .build();
        SubRole lv3 = SubRole.builder()
            .subRoleName(ESubRole.LV3)
            .numberOfCollection(4)
            .numberOfClothes(4)
            .build();
        subRoleRepository.save(lv1);
        subRoleRepository.save(lv2);
        subRoleRepository.save(lv3);
      }
    };
  }
}
