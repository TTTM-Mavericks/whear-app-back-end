package com.tttm.Whear.App;

import com.tttm.Whear.App.entity.BodyShape;
import com.tttm.Whear.App.entity.RuleMatchingClothes;
import com.tttm.Whear.App.entity.Style;
import com.tttm.Whear.App.entity.SubRole;
import com.tttm.Whear.App.enums.BodyShapeType;
import com.tttm.Whear.App.enums.ESubRole;
import com.tttm.Whear.App.enums.StyleType;
import com.tttm.Whear.App.repository.BodyShapeRepository;
import com.tttm.Whear.App.repository.RuleMatchingClothesRepository;
import com.tttm.Whear.App.repository.StyleRepository;
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
                        .price(2000)
                        .build();
                SubRole lv2 = SubRole.builder()
                        .subRoleName(ESubRole.LV2)
                        .numberOfCollection(3)
                        .numberOfClothes(3)
                        .price(4000)
                        .build();
                SubRole lv3 = SubRole.builder()
                        .subRoleName(ESubRole.LV3)
                        .numberOfCollection(4)
                        .numberOfClothes(4)
                        .price(6000)
                        .build();
                subRoleRepository.save(lv1);
                subRoleRepository.save(lv2);
                subRoleRepository.save(lv3);
            }
        };
    }

    @Bean
    public CommandLineRunner createRuleMatchingClothes(RuleMatchingClothesRepository ruleMatchingClothesRepository, StyleRepository styleRepository,
                                                       BodyShapeRepository bodyShapeRepository) {
        return arg -> {
            if (styleRepository.findAll().size() == 0) {
                Style CYBERPUNK = Style.builder()
                        .styleName(StyleType.CYBERPUNK)
                        .build();
                Style CLASSIC = Style.builder()
                        .styleName(StyleType.CLASSIC)
                        .build();
                Style ROCK = Style.builder()
                        .styleName(StyleType.ROCK)
                        .build();
                Style PREPPY = Style.builder()
                        .styleName(StyleType.PREPPY)
                        .build();
                Style NORMCORE = Style.builder()
                        .styleName(StyleType.NORMCORE)
                        .build();
                Style MINIMALISM = Style.builder()
                        .styleName(StyleType.MINIMALISM)
                        .build();
                Style BASIC = Style.builder()
                        .styleName(StyleType.BASIC)
                        .build();
                Style SPORTY = Style.builder()
                        .styleName(StyleType.SPORTY)
                        .build();
                Style PARISIAN = Style.builder()
                        .styleName(StyleType.PARISIAN)
                        .build();
                Style GOTHIC = Style.builder()
                        .styleName(StyleType.GOTHIC)
                        .build();
                Style BOHEMIAN = Style.builder()
                        .styleName(StyleType.BOHEMIAN)
                        .build();
                Style Y2K = Style.builder()
                        .styleName(StyleType.Y2K)
                        .build();
                Style OLD_MONEY = Style.builder()
                        .styleName(StyleType.OLD_MONEY)
                        .build();
                Style HIPPIE = Style.builder()
                        .styleName(StyleType.HIPPIE)
                        .build();
                Style VINTAGE = Style.builder()
                        .styleName(StyleType.VINTAGE)
                        .build();
                Style INDIE = Style.builder()
                        .styleName(StyleType.INDIE)
                        .build();
                Style E_GIRL = Style.builder()
                        .styleName(StyleType.E_GIRL)
                        .build();
                styleRepository.save(CYBERPUNK);
                styleRepository.save(CLASSIC);
                styleRepository.save(ROCK);
                styleRepository.save(PREPPY);
                styleRepository.save(NORMCORE);
                styleRepository.save(MINIMALISM);
                styleRepository.save(BASIC);
                styleRepository.save(SPORTY);
                styleRepository.save(PARISIAN);
                styleRepository.save(GOTHIC);
                styleRepository.save(BOHEMIAN);
                styleRepository.save(Y2K);
                styleRepository.save(OLD_MONEY);
                styleRepository.save(HIPPIE);
                styleRepository.save(VINTAGE);
                styleRepository.save(INDIE);
                styleRepository.save(E_GIRL);
            }
            if (bodyShapeRepository.findAll().size() == 0) {
                BodyShape HOURGLASS_SHAPE = BodyShape.builder()
                        .bodyShapeName(BodyShapeType.HOURGLASS_SHAPE)
                        .build();
                BodyShape PEAR_SHAPE = BodyShape.builder()
                        .bodyShapeName(BodyShapeType.PEAR_SHAPE)
                        .build();
                BodyShape APPLE_SHAPE = BodyShape.builder()
                        .bodyShapeName(BodyShapeType.APPLE_SHAPE)
                        .build();
                BodyShape RECTANGLE_SHAPE = BodyShape.builder()
                        .bodyShapeName(BodyShapeType.RECTANGLE_SHAPE)
                        .build();
                BodyShape INVERTED_TRIANGLE_SHAPE = BodyShape.builder()
                        .bodyShapeName(BodyShapeType.INVERTED_TRIANGLE_SHAPE)
                        .build();
                BodyShape LEAN_OR_SLIM_SHAPE = BodyShape.builder()
                        .bodyShapeName(BodyShapeType.LEAN_OR_SLIM_SHAPE)
                        .build();
                BodyShape ROUND_SHAPE = BodyShape.builder()
                        .bodyShapeName(BodyShapeType.ROUND_SHAPE)
                        .build();
                bodyShapeRepository.save(HOURGLASS_SHAPE);
                bodyShapeRepository.save(PEAR_SHAPE);
                bodyShapeRepository.save(APPLE_SHAPE);
                bodyShapeRepository.save(RECTANGLE_SHAPE);
                bodyShapeRepository.save(INVERTED_TRIANGLE_SHAPE);
                bodyShapeRepository.save(LEAN_OR_SLIM_SHAPE);
                bodyShapeRepository.save(ROUND_SHAPE);
            }
            if(ruleMatchingClothesRepository.findAll().size() == 0){
                RuleMatchingClothes rule2 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("CYBERPUNK").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("PEAR_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("GRAY")
                        .topOutside("HOODIE")
                        .topOutsideColor("BLACK")
                        .topMaterial("FLEECE")
                        .bottomKind("JOGGER")
                        .bottomColor("BLACK")
                        .bottomMaterial("POLYESTER")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("XANH_NEON")
                        .accessoryKind("CAP")
                        .accessoryMaterial("FABRIC")
                        .build();
                ruleMatchingClothesRepository.save(rule2);

                RuleMatchingClothes rule3 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("CYBERPUNK").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("APPLE_SHAPE").getBodyShapeID())
                        .topInside("CROP_TOP")
                        .topInsideColor("WHITE")
                        .topOutside("X")
                        .topOutsideColor("BLACK")
                        .topMaterial("COTTON")
                        .bottomKind("JEANS")
                        .bottomColor("AQUA_NEON")
                        .bottomMaterial("COTTON")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("X")
                        .accessoryMaterial("X")
                        .build();
                ruleMatchingClothesRepository.save(rule3);

                RuleMatchingClothes rule4 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("CYBERPUNK").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("RECTANGLE_SHAPE").getBodyShapeID())
                        .topInside("CROP_TOP")
                        .topInsideColor("GRAY")
                        .topOutside("X")
                        .topOutsideColor("BLACK")
                        .topMaterial("COTTON")
                        .bottomKind("JOGGER")
                        .bottomColor("BLUE_NEON")
                        .bottomMaterial("POLYESTER")
                        .shoesType("DR_MARTENS")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("HANDBAG")
                        .accessoryMaterial("FABRIC")
                        .build();
                ruleMatchingClothesRepository.save(rule4);

                RuleMatchingClothes rule5 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("CYBERPUNK").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("INVERTED_TRIANGLE_SHAPE").getBodyShapeID())
                        .topInside("CROP_TOP")
                        .topInsideColor("HỐNG_NEON")
                        .topOutside("X")
                        .topOutsideColor("XANH_NEON")
                        .topMaterial("COTTON")
                        .bottomKind("JOGGER")
                        .bottomColor("XANH_NEON")
                        .bottomMaterial("POLYESTER")
                        .shoesType("DR_MARTENS")
                        .shoesTypeColor("YELLOW_NEON")
                        .accessoryKind("X")
                        .accessoryMaterial("X")
                        .build();
                ruleMatchingClothesRepository.save(rule4);

                RuleMatchingClothes rule6 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("CYBERPUNK").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("LEAN_OR_SLIM_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("BURNING_RED")
                        .topOutside("X")
                        .topOutsideColor("BLACK")
                        .topMaterial("COTTON")
                        .bottomKind("SKINNY")
                        .bottomColor("GRAY")
                        .bottomMaterial("DENIM")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("X")
                        .accessoryMaterial("X")
                        .build();
                ruleMatchingClothesRepository.save(rule6);

                RuleMatchingClothes rule7 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("CLASSIC").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("HOURGLASS_SHAPE").getBodyShapeID())
                        .topInside("X")
                        .topInsideColor("WHITE")
                        .topOutside("SHIRT")
                        .topOutsideColor("BLACK")
                        .topMaterial("COTTON")
                        .bottomKind("PANTS")
                        .bottomColor("BROWN")
                        .bottomMaterial("COTTON")
                        .shoesType("LOAFERS")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("WATCH")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule7);

                RuleMatchingClothes rule8 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("CLASSIC").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("PEAR_SHAPE").getBodyShapeID())
                        .topInside("X")
                        .topInsideColor("WHITE")
                        .topOutside("POLO_SHIRT")
                        .topOutsideColor("BLUE_NAVY")
                        .topMaterial("COTTON")
                        .bottomKind("WIDE_LEG_PANT")
                        .bottomColor("BLACK")
                        .bottomMaterial("LINEN")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("BLUE_NAVY")
                        .accessoryKind("WATCH")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule8);

                RuleMatchingClothes rule9 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("CLASSIC").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("APPLE_SHAPE").getBodyShapeID())
                        .topInside("X")
                        .topInsideColor("GRAY")
                        .topOutside("SHIRT")
                        .topOutsideColor("BLACK")
                        .topMaterial("COTTON")
                        .bottomKind("WIDE_LEG_PANT")
                        .bottomColor("BEIGE")
                        .bottomMaterial("LINEN")
                        .shoesType("DR.MARTENS")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("BEANIE")
                        .accessoryMaterial("FABRIC")
                        .build();
                ruleMatchingClothesRepository.save(rule9);

                RuleMatchingClothes rule10 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("CLASSIC").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("RECTANGLE_SHAPE").getBodyShapeID())
                        .topInside("X")
                        .topInsideColor("WHITE")
                        .topOutside("SHIRT")
                        .topOutsideColor("XANH_OLIVE")
                        .topMaterial("COTTON")
                        .bottomKind("SKIRT")
                        .bottomColor("BLACK")
                        .bottomMaterial("COTTON")
                        .shoesType("OXFORD")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("NECKLACE")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule10);

                RuleMatchingClothes rule11 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("CLASSIC").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("INVERTED_TRIANGLE_SHAPE").getBodyShapeID())
                        .topInside("X")
                        .topInsideColor("GRAY")
                        .topOutside("SHIRT")
                        .topOutsideColor("BROWN")
                        .topMaterial("COTTON")
                        .bottomKind("SKIRT")
                        .bottomColor("ĐỎ_BURGUNDY")
                        .bottomMaterial("POLYESTER")
                        .shoesType("LOAFERS")
                        .shoesTypeColor("BROWN")
                        .accessoryKind("HANDBAG")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule11);

                RuleMatchingClothes rule12 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("CLASSIC").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("ROUND_SHAPE").getBodyShapeID())
                        .topInside("X")
                        .topInsideColor("WHITE")
                        .topOutside("SHIRT")
                        .topOutsideColor("BLUE_NAVY")
                        .topMaterial("COTTON")
                        .bottomKind("PANTS")
                        .bottomColor("BURNING_RED")
                        .bottomMaterial("COTTON")
                        .shoesType("CHELSEA_BOOTS")
                        .shoesTypeColor("BLUE_NAVY")
                        .accessoryKind("SUNGLASSES")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule12);
            }
        };
    }
}
