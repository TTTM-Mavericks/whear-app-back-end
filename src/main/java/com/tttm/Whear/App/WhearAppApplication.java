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
                ruleMatchingClothesRepository.save(rule5);

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
                        .shoesType("DR_MARTENS")
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
                RuleMatchingClothes rule13 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("ROCK").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("HOURGLASS_SHAPE").getBodyShapeID())
                        .topInside("X")
                        .topInsideColor("WHITE")
                        .topOutside("T_SHIRT")
                        .topOutsideColor("BLACK")
                        .topMaterial("COTTON")
                        .bottomKind("JEANS")
                        .bottomColor("BLACK")
                        .bottomMaterial("COTTON")
                        .shoesType("BOOTS")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("WATCH")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule13);


                RuleMatchingClothes rule14 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("ROCK").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("PEAR_SHAPE").getBodyShapeID())
                        .topInside("SHIRT")
                        .topInsideColor("WHITE")
                        .topOutside("JACKET")
                        .topOutsideColor("RED")
                        .topMaterial("LEATHER")
                        .bottomKind("JEANS")
                        .bottomColor("RED")
                        .bottomMaterial("COTTON")
                        .shoesType("BOOTS")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("NECKLACE")
                        .accessoryMaterial("FABRIC")
                        .build();
                ruleMatchingClothesRepository.save(rule14);

                RuleMatchingClothes rule15 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("ROCK").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("RECTANGLE_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("WHITE")
                        .topOutside("BLAZER")
                        .topOutsideColor("DARK_BLUE")
                        .topMaterial("LEATHER")
                        .bottomKind("JEANS")
                        .bottomColor("BLACK")
                        .bottomMaterial("COTTON")
                        .shoesType("BOOTS")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("EARINGS")
                        .accessoryMaterial("LEATHER")
                        .build();
                ruleMatchingClothesRepository.save(rule15);

                RuleMatchingClothes rule16 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("ROCK").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("INVERTED_TRIANGLE_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("BLACK")
                        .topOutside("BOMBER")
                        .topOutsideColor("GRAY")
                        .topMaterial("LEATHER")
                        .bottomKind("JEANS")
                        .bottomColor("BLACK")
                        .bottomMaterial("COTTON")
                        .shoesType("BOOTS")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("NECKLACE")
                        .accessoryMaterial("LEATHER")
                        .build();
                ruleMatchingClothesRepository.save(rule16);

                RuleMatchingClothes rule17 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("ROCK").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("ROUND_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("WHITE_CREAM")
                        .topOutside("HOODIE")
                        .topOutsideColor("BROWN")
                        .topMaterial("COTTON")
                        .bottomKind("JEANS")
                        .bottomColor("BLACK")
                        .bottomMaterial("COTTON")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("SNAPBACK")
                        .accessoryMaterial("FABRIC")
                        .build();
                ruleMatchingClothesRepository.save(rule17);

                RuleMatchingClothes rule18 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("ROCK").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("LEAN_OR_SLIM_SHAPE").getBodyShapeID())
                        .topInside("SHIRT")
                        .topInsideColor("ORANGE")
                        .topOutside("BLAZER")
                        .topOutsideColor("BLACK")
                        .topMaterial("FLANNEL")
                        .bottomKind("JEANS")
                        .bottomColor("BLACK")
                        .bottomMaterial("COTTON")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("SUNGLASSES")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule18);

                RuleMatchingClothes rule19 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("PREPPY").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("HOURGLASS_SHAPE").getBodyShapeID())
                        .topInside("POLO_SHIRT")
                        .topInsideColor("WHITE")
                        .topOutside("X")
                        .topOutsideColor("BLUE_NAVY")
                        .topMaterial("COTTON")
                        .bottomKind("PANTS")
                        .bottomColor("BEIGE")
                        .bottomMaterial("COTTON")
                        .shoesType("LOAFERS")
                        .shoesTypeColor("RED")
                        .accessoryKind("WATCH")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule19);

                RuleMatchingClothes rule20 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("PREPPY").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("PEAR_SHAPE").getBodyShapeID())
                        .topInside("SHIRT")
                        .topInsideColor("HỒNG_PASTEL")
                        .topOutside("SWEATER")
                        .topOutsideColor("XANH_PASTEL")
                        .topMaterial("WOOL")
                        .bottomKind("SKIRT")
                        .bottomColor("WHITE")
                        .bottomMaterial("POLYESTER")
                        .shoesType("OXFORD")
                        .shoesTypeColor("WHITE")
                        .accessoryKind("HANDBAG")
                        .accessoryMaterial("LEATHER")
                        .build();
                ruleMatchingClothesRepository.save(rule20);

                RuleMatchingClothes rule21 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("PREPPY").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("APPLE_SHAPE").getBodyShapeID())
                        .topInside("SHIRT")
                        .topInsideColor("BLUE_NAVY")
                        .topOutside("SWEATER")
                        .topOutsideColor("NÂU_VÀNG_NHẠT")
                        .topMaterial("COTTON")
                        .bottomKind("WIDE_LEG_PANT")
                        .bottomColor("BLACK")
                        .bottomMaterial("LINEN")
                        .shoesType("LOAFERS")
                        .shoesTypeColor("WHITE")
                        .accessoryKind("HANDBAG")
                        .accessoryMaterial("LEATHER")
                        .build();
                ruleMatchingClothesRepository.save(rule21);

                RuleMatchingClothes rule22 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("PREPPY").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("RECTANGLE_SHAPE").getBodyShapeID())
                        .topInside("POLO_SHIRT")
                        .topInsideColor("WHITE")
                        .topOutside("X")
                        .topOutsideColor("X")
                        .topMaterial("COTTON")
                        .bottomKind("SKIRT")
                        .bottomColor("RED")
                        .bottomMaterial("COTTON")
                        .shoesType("LOAFERS")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("HANDBAG")
                        .accessoryMaterial("FABRIC")
                        .build();
                ruleMatchingClothesRepository.save(rule22);

                RuleMatchingClothes rule23 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("PREPPY").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("ROUND_SHAPE").getBodyShapeID())
                        .topInside("SHIRT")
                        .topInsideColor("WHITE")
                        .topOutside("BLAZER")
                        .topOutsideColor("X")
                        .topMaterial("FLANNEL")
                        .bottomKind("SKIRT")
                        .bottomColor("XANH_PASTEL")
                        .bottomMaterial("COTTON")
                        .shoesType("OXFORD")
                        .shoesTypeColor("PINK")
                        .accessoryKind("HANDBAG")
                        .accessoryMaterial("FABRIC")
                        .build();
                ruleMatchingClothesRepository.save(rule23);

                RuleMatchingClothes rule24 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("PREPPY").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("LEAN_OR_SLIM_SHAPE").getBodyShapeID())
                        .topInside("POLO_SHIRT")
                        .topInsideColor("BLACK")
                        .topOutside("X")
                        .topOutsideColor("NÂU_VÀNG_NHẠT")
                        .topMaterial("COTTON")
                        .bottomKind("SKIRT")
                        .bottomColor("WHITE")
                        .bottomMaterial("COTTON")
                        .shoesType("OXFORD")
                        .shoesTypeColor("WHITE")
                        .accessoryKind("SUNGLASSES")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule24);

                RuleMatchingClothes rule25 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("NORMCORE").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("HOURGLASS_SHAPE").getBodyShapeID())
                        .topInside("SHIRT")
                        .topInsideColor("GRAY")
                        .topOutside("SWEATER")
                        .topOutsideColor("WHITE")
                        .topMaterial("WOOL")
                        .bottomKind("JEANS")
                        .bottomColor("BLACK")
                        .bottomMaterial("COTTON")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("WHITE")
                        .accessoryKind("HANDBAG")
                        .accessoryMaterial("LEATHER")
                        .build();
                ruleMatchingClothesRepository.save(rule25);

                RuleMatchingClothes rule26 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("NORMCORE").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("PEAR_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("BLACK")
                        .topOutside("HOODIE")
                        .topOutsideColor("WHITE_CREAM")
                        .topMaterial("COTTON")
                        .bottomKind("JOGGER")
                        .bottomColor("BROWN")
                        .bottomMaterial("POLYESTER")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("NUDE")
                        .accessoryKind("SNAPBACK")
                        .accessoryMaterial("FABRIC")
                        .build();
                ruleMatchingClothesRepository.save(rule26);

                RuleMatchingClothes rule27 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("NORMCORE").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("RECTANGLE_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("WHITE")
                        .topOutside("X")
                        .topOutsideColor("GRAY")
                        .topMaterial("LEATHER")
                        .bottomKind("JEANS")
                        .bottomColor("BLACK")
                        .bottomMaterial("COTTON")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("GRAY")
                        .accessoryKind("SNAPBACK")
                        .accessoryMaterial("FABRIC")
                        .build();
                ruleMatchingClothesRepository.save(rule27);

                RuleMatchingClothes rule28 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("NORMCORE").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("INVERTED_TRIANGLE_SHAPE").getBodyShapeID())
                        .topInside("LONG_SLEEVE")
                        .topInsideColor("WHITE")
                        .topOutside("SWEATER")
                        .topOutsideColor("BLACK")
                        .topMaterial("WOOL")
                        .bottomKind("WIDE_LEG_PANT")
                        .bottomColor("BLUE")
                        .bottomMaterial("LINEN")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("EARINGS")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule28);

                RuleMatchingClothes rule29 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("NORMCORE").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("ROUND_SHAPE").getBodyShapeID())
                        .topInside("LONG_SLEEVE")
                        .topInsideColor("HỒNG_NHẠT")
                        .topOutside("SWEATER")
                        .topOutsideColor("GRAY")
                        .topMaterial("WOOL")
                        .bottomKind("JOGGER")
                        .bottomColor("WHITE")
                        .bottomMaterial("POLYESTER")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("GRAY")
                        .accessoryKind("NECKLACE")
                        .accessoryMaterial("FABRIC")
                        .build();
                ruleMatchingClothesRepository.save(rule29);


                RuleMatchingClothes rule30 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("NORMCORE").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("LEAN_OR_SLIM_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("BLACK")
                        .topOutside("X")
                        .topOutsideColor("BROWN")
                        .topMaterial("FLANNEL")
                        .bottomKind("JEANS")
                        .bottomColor("WHITE")
                        .bottomMaterial("COTTON")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("BROWN")
                        .accessoryKind("SUNGLASSES")
                        .accessoryMaterial("FABRIC")
                        .build();
                ruleMatchingClothesRepository.save(rule30);

                RuleMatchingClothes rule31 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("MINIMALISM").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("PEAR_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("WHITE")
                        .topOutside("X")
                        .topOutsideColor("BLACK")
                        .topMaterial("COTTON")
                        .bottomKind("JOGGER")
                        .bottomColor("GRAY")
                        .bottomMaterial("COTTON")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("BRACELET")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule31);

                RuleMatchingClothes rule32 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("MINIMALISM").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("HOURGLASS_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("WHITE")
                        .topOutside("BLAZER")
                        .topOutsideColor("WHITE")
                        .topMaterial("COTTON")
                        .bottomKind("BAGGY")
                        .bottomColor("GRAY")
                        .bottomMaterial("COTTON")
                        .shoesType("LOAFERS")
                        .shoesTypeColor("HỒNG_NHẠT")
                        .accessoryKind("BRACELET")
                        .accessoryMaterial("GOLD")
                        .build();
                ruleMatchingClothesRepository.save(rule32);

                RuleMatchingClothes rule33 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("MINIMALISM").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("APPLE_SHAPE").getBodyShapeID())
                        .topInside("X")
                        .topInsideColor("GRAY")
                        .topOutside("HOODIE")
                        .topOutsideColor("HỒNG_NHẠT")
                        .topMaterial("FLEECE")
                        .bottomKind("CARGO_PANT")
                        .bottomColor("DARK_GREEN")
                        .bottomMaterial("NYLON")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("GRAY")
                        .accessoryKind("BRACELET")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule33);

                RuleMatchingClothes rule34 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("MINIMALISM").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("RECTANGLE_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("BLUE_NAVY")
                        .topOutside("SWEATER")
                        .topOutsideColor("X")
                        .topMaterial("WOOL")
                        .bottomKind("SKINNY")
                        .bottomColor("WHITE")
                        .bottomMaterial("DENIM")
                        .shoesType("CHELSEA_BOOTS")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("BEANIE")
                        .accessoryMaterial("COTTON")
                        .build();
                ruleMatchingClothesRepository.save(rule34);

                RuleMatchingClothes rule35 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("MINIMALISM").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("INVERTED_TRIANGLE_SHAPE").getBodyShapeID())
                        .topInside("SHIRT")
                        .topInsideColor("WHITE")
                        .topOutside("BLAZER")
                        .topOutsideColor("BROWN")
                        .topMaterial("COTTON")
                        .bottomKind("SUIT_PANTS")
                        .bottomColor("WHITE")
                        .bottomMaterial("POLYESTER")
                        .shoesType("OXFORD")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("WATCH")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule35);

                RuleMatchingClothes rule36 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("MINIMALISM").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("HOURGLASS_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("GRAY")
                        .topOutside("SATIN_BLOUSE")
                        .topOutsideColor("XANH_OLIVE")
                        .topMaterial("SATIN")
                        .bottomKind("SKINNY")
                        .bottomColor("WHITE")
                        .bottomMaterial("DENIM")
                        .shoesType("PUMPS")
                        .shoesTypeColor("WHITE")
                        .accessoryKind("EARINGS")
                        .accessoryMaterial("GOLD")
                        .build();
                ruleMatchingClothesRepository.save(rule36);

                RuleMatchingClothes rule37 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("MINIMALISM").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("APPLE_SHAPE").getBodyShapeID())
                        .topInside("TANK_TOP")
                        .topInsideColor("BLACK")
                        .topOutside("X")
                        .topOutsideColor("GRAY")
                        .topMaterial("SPANDEX")
                        .bottomKind("JOGGER")
                        .bottomColor("WHITE")
                        .bottomMaterial("NYLON")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("BRACELET")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule37);

                RuleMatchingClothes rule38 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("MINIMALISM").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("RECTANGLE_SHAPE").getBodyShapeID())
                        .topInside("SHIRT")
                        .topInsideColor("X")
                        .topOutside("BLAZER")
                        .topOutsideColor("X")
                        .topMaterial("POLYESTER")
                        .bottomKind("PENCIL_SKIRT")
                        .bottomColor("X")
                        .bottomMaterial("POLYESTER")
                        .shoesType("THIGH_HIGH_BOOTS")
                        .shoesTypeColor("X")
                        .accessoryKind("WATCH")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule38);

                RuleMatchingClothes rule39 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("MINIMALISM").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("INVERTED_TRIANGLE_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("X")
                        .topOutside("BLAZER")
                        .topOutsideColor("X")
                        .topMaterial("COTTON")
                        .bottomKind("WIDE_LEG_PANT")
                        .bottomColor("X")
                        .bottomMaterial("LINEN")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("X")
                        .accessoryKind("NECKLACE")
                        .accessoryMaterial("SILVER")
                        .build();
                ruleMatchingClothesRepository.save(rule39);

                RuleMatchingClothes rule40 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("BASIC").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("HOURGLASS_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("BLACK")
                        .topOutside("X")
                        .topOutsideColor("WHITE")
                        .topMaterial("COTTON")
                        .bottomKind("BAGGY")
                        .bottomColor("GRAY")
                        .bottomMaterial("DENIM")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("GRAY")
                        .accessoryKind("BRACELET")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule40);

                RuleMatchingClothes rule41 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("BASIC").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("APPLE_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("WHITE")
                        .topOutside("HOODIE")
                        .topOutsideColor("BROWN")
                        .topMaterial("FLEECE")
                        .bottomKind("CARGO_PANT")
                        .bottomColor("BLUE_NAVY")
                        .bottomMaterial("NYLON")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("BROWN")
                        .accessoryKind("BRACELET")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule41);

                RuleMatchingClothes rule42 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("BASIC").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("INVERTED_TRIANGLE_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("BEIGE")
                        .topOutside("DRESS")
                        .topOutsideColor("BLACK")
                        .topMaterial("JERSEY")
                        .bottomKind("X")
                        .bottomColor("XANH_OLIVE")
                        .bottomMaterial("X")
                        .shoesType("PUMPS")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("BRACELET")
                        .accessoryMaterial("GOLD")
                        .build();
                ruleMatchingClothesRepository.save(rule42);

                RuleMatchingClothes rule43 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("BASIC").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("HOURGLASS_SHAPE").getBodyShapeID())
                        .topInside("X")
                        .topInsideColor("GRAY")
                        .topOutside("X")
                        .topOutsideColor("BLACK")
                        .topMaterial("FLANNEL")
                        .bottomKind("JEANS")
                        .bottomColor("BLACK")
                        .bottomMaterial("COTTON")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("RED")
                        .accessoryKind("SUNGLASSES")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule43);

                RuleMatchingClothes rule44 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("BASIC").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("HOURGLASS_SHAPE").getBodyShapeID())
                        .topInside("SHIRT")
                        .topInsideColor("WHITE")
                        .topOutside("X")
                        .topOutsideColor("BLUE_NAVY")
                        .topMaterial("COTTON")
                        .bottomKind("WIDE_LEG_PANT")
                        .bottomColor("RED")
                        .bottomMaterial("LINEN")
                        .shoesType("SANDALS")
                        .shoesTypeColor("BLUE_NAVY")
                        .accessoryKind("EARINGS")
                        .accessoryMaterial("GOLD")
                        .build();
                ruleMatchingClothesRepository.save(rule44);

                RuleMatchingClothes rule45 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("BASIC").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("RECTANGLE_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("BEIGE")
                        .topOutside("X")
                        .topOutsideColor("BROWN")
                        .topMaterial("COTTON")
                        .bottomKind("HIGH_WAISTED_SHORTS")
                        .bottomColor("XANH_OLIVE")
                        .bottomMaterial("DENIM")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("BROWN")
                        .accessoryKind("BRACELET")
                        .accessoryMaterial("LEATHER")
                        .build();
                ruleMatchingClothesRepository.save(rule45);

                RuleMatchingClothes rule46 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("SPORTY").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("HOURGLASS_SHAPE").getBodyShapeID())
                        .topInside("POLO_SHIRT")
                        .topInsideColor("WHITE")
                        .topOutside("X")
                        .topOutsideColor("BLACK")
                        .topMaterial("SPANDEX")
                        .bottomKind("JOGGER")
                        .bottomColor("BLACK")
                        .bottomMaterial("NYLON")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("RED")
                        .accessoryKind("SUNGLASSES")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule46);

                RuleMatchingClothes rule47 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("SPORTY").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("LEAN_OR_SLIM_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("WHITE")
                        .topOutside("X")
                        .topOutsideColor("BLUE_NAVY")
                        .topMaterial("POLYESTER")
                        .bottomKind("JOGGER")
                        .bottomColor("GRAY")
                        .bottomMaterial("SPANDEX")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("WHITE")
                        .accessoryKind("WATCH")
                        .accessoryMaterial("TITAN")
                        .build();
                ruleMatchingClothesRepository.save(rule47);

                RuleMatchingClothes rule48 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("SPORTY").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("ROUND_SHAPE").getBodyShapeID())
                        .topInside("TANK_TOP")
                        .topInsideColor("BLACK")
                        .topOutside("X")
                        .topOutsideColor("GRAY")
                        .topMaterial("SPANDEX")
                        .bottomKind("SHORTS")
                        .bottomColor("BLACK")
                        .bottomMaterial("COTTON")
                        .shoesType("RUNNING_SHOES")
                        .shoesTypeColor("CAM_NEON")
                        .accessoryKind("SUNGLASSES")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule48);

                RuleMatchingClothes rule49 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("SPORTY").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("HOURGLASS_SHAPE").getBodyShapeID())
                        .topInside("SPORT_BRA")
                        .topInsideColor("GRAY")
                        .topOutside("X")
                        .topOutsideColor("PINK")
                        .topMaterial("SPANDEX")
                        .bottomKind("LEGGING")
                        .bottomColor("BLACK")
                        .bottomMaterial("SPANDEX")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("BLACK")
                        .accessoryKind("NECKLACE")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule49);

                RuleMatchingClothes rule50 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("SPORTY").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("INVERTED_TRIANGLE_SHAPE").getBodyShapeID())
                        .topInside("JACKET_ZIP")
                        .topInsideColor("WHITE")
                        .topOutside("X")
                        .topOutsideColor("DARK_GREEN")
                        .topMaterial("FLEECE")
                        .bottomKind("JOGGER")
                        .bottomColor("BLACK")
                        .bottomMaterial("NYLON")
                        .shoesType("HIGH_TOP_SNEAKER")
                        .shoesTypeColor("WHITE")
                        .accessoryKind("BRACELET")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule50);

                RuleMatchingClothes rule51 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("SPORTY").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("LEAN_OR_SLIM_SHAPE").getBodyShapeID())
                        .topInside("T_SHIRT")
                        .topInsideColor("GRAY")
                        .topOutside("X")
                        .topOutsideColor("RED")
                        .topMaterial("FLANNEL")
                        .bottomKind("JEANS")
                        .bottomColor("WHITE")
                        .bottomMaterial("COTTON")
                        .shoesType("SNEAKER")
                        .shoesTypeColor("RED")
                        .accessoryKind("SUNGLASSES")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule51);

                RuleMatchingClothes rule52 = RuleMatchingClothes.builder()
                        .styleID(styleRepository.getStyleByStyleName("SPORTY").getStyleID())
                        .bodyShapeID(
                                bodyShapeRepository.getBodyShapeByBodyShapeName("ROUND_SHAPE").getBodyShapeID())
                        .topInside("CROP_TOP")
                        .topInsideColor("BLACK")
                        .topOutside("X")
                        .topOutsideColor("GRAY")
                        .topMaterial("SPANDEX")
                        .bottomKind("JOGGER")
                        .bottomColor("BLACK")
                        .bottomMaterial("COTTON")
                        .shoesType("RUNNING_SHOES")
                        .shoesTypeColor("CAM_NEON")
                        .accessoryKind("SUNGLASSES")
                        .accessoryMaterial("METAL")
                        .build();
                ruleMatchingClothesRepository.save(rule52);
            }
        };
    }
}
