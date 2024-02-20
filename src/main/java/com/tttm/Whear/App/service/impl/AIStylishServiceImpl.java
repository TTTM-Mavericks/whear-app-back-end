package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.constant.ConstantMessage;
import com.tttm.Whear.App.constant.ConstantString;
import com.tttm.Whear.App.entity.*;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.service.*;
import com.tttm.Whear.App.utils.request.MemoryRequest;
import com.tttm.Whear.App.utils.request.RejectClothesRequest;
import com.tttm.Whear.App.utils.request.SuggestChoiceForPremiumUser;
import com.tttm.Whear.App.utils.response.AIStylishResponse;
import com.tttm.Whear.App.utils.response.ClothesResponse;
import com.tttm.Whear.App.utils.response.RuleMatchingClothesResponse;
import com.tttm.Whear.App.utils.response.UserResponseStylish;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class AIStylishServiceImpl implements AIStylishService {
    private final UserService userService;
    private final ClothesService clothesService;
    private final CustomerService customerService;
    private final RuleMatchingClothesService ruleMatchingClothesService;
    private final SubroleService subroleService;
    private final BodyShapeService bodyShapeService;
    private final StyleService styleService;
    private final MemoryEntityService memoryEntityService;
    private final Random random = new Random();
    private final Logger logger = LoggerFactory.getLogger(AIStylishServiceImpl.class);


    @Override
    public List<AIStylishResponse> getSuggestClothesForUser(String userID) throws CustomException {
        // Check ID User or User Entity is exist or not
        Optional.of(userID)
                .filter(id -> !id.isEmpty() && !id.isBlank())
                .orElseThrow(() -> new CustomException(ConstantMessage.USERID_IS_EMPTY_OR_NOT_EXIST.getMessage()));

        User user = Optional.ofNullable(userService.getUserEntityByUserID(userID))
                .orElseThrow(() -> new CustomException(ConstantMessage.CANNOT_FIND_USER_BY_USERID.getMessage()));

        // Get BodyShape by Specific User
        BodyShape bodyShape = user.getBodyShape();

        // Get List Style By Specific User
        List<Style> styleList = styleService.getListStyleByUserID(userID);

        // Check SubRole is Free or Premium
        Customer customer = customerService.getCustomerByID(userID);
        List<AIStylishResponse> outfitsForWeek = new ArrayList<>();

        UserResponseStylish userResponseStylish = UserResponseStylish
                .builder()
                .userID(user.getUserID())
                .username(user.getUsername())
                .imgUrl(user.getImgUrl())
                .build();

        switch (subroleService.getSubroleByID(customer.getSubRoleID()).getSubRoleName()) {
            case LV1:
                outfitsForWeek = suggestClothesForFreeUser(user, bodyShape, styleList, userResponseStylish);
                break;
            case LV2:
                outfitsForWeek = suggestClothesForPremiumUser(user, bodyShape, styleList, userResponseStylish);
                break;
        }
        return outfitsForWeek;
    }

    private List<AIStylishResponse> suggestClothesForFreeUser(User user, BodyShape bodyShape, List<Style> styleList,  UserResponseStylish userResponseStylish) throws CustomException {
        List<RuleMatchingClothesResponse> ruleMatchingClothesResponses = styleList
                .stream()
                .map(style -> ruleMatchingClothesService.getRuleMatchingClothesByStyleAndBodyShape(style.getStyleID(), bodyShape.getBodyShapeID()))
                .collect(Collectors.toList());

        List<AIStylishResponse> aiStylishResponses = new ArrayList<>();
        int index = 0;

        for (RuleMatchingClothesResponse rule : ruleMatchingClothesResponses) {
            List<List<ClothesResponse>> outfitList = new ArrayList<>();

            List<ClothesResponse> topInsideClothes = clothesService.getClothesBaseOnTypeOfClothesAndColorOrMaterials(
                    rule.getTopInside(), rule.getTopInsideColor(), rule.getTopMaterial());

            for (ClothesResponse clothesResponse : topInsideClothes) {
                logger.warn("This is Top Inside Clothes {}", clothesResponse);
            }

            List<ClothesResponse> topOutsideClothes = clothesService.getClothesBaseOnTypeOfClothesAndColorOrMaterials(
                    rule.getTopOutside(), rule.getTopOutsideColor(), rule.getTopMaterial());

            for (ClothesResponse clothesResponse : topOutsideClothes) {
                logger.warn("This is Top Outside Clothes {}", clothesResponse);
            }

            List<ClothesResponse> bottomClothes = clothesService.getClothesBaseOnTypeOfClothesAndColorOrMaterials(
                    rule.getBottomKind(), rule.getBottomColor(), rule.getBottomMaterial());

            for (ClothesResponse clothesResponse : bottomClothes) {
                logger.warn("This is Bottom Clothes {}", clothesResponse);
            }

            List<ClothesResponse> shoesClothes = clothesService.getClothesBaseOnTypeOfClothesAndColorOrMaterials(
                    rule.getShoesType(), rule.getShoesTypeColor(), rule.getBottomMaterial());

            for (ClothesResponse clothesResponse : shoesClothes) {
                logger.warn("This is Shoes Clothes {}", clothesResponse);
            }

            List<ClothesResponse> accessoriesClothes = clothesService.getClothesBaseOnTypeOfClothesAndMaterial(
                    rule.getAccessoryKind(), rule.getAccessoryMaterial());

            for (ClothesResponse clothesResponse : accessoriesClothes) {
                logger.warn("This is Accessories Clothes {}", clothesResponse);
            }

            if (styleList.size() >= 2) index++;
            String message = "";

            List<List<ClothesResponse>> outfits = selectUniqueOutfits(
                    topInsideClothes,
                    topOutsideClothes,
                    bottomClothes,
                    shoesClothes,
                    accessoriesClothes,
                    rule.getTopInsideColor(),
                    rule.getTopOutsideColor(),
                    rule.getBottomColor(),
                    rule.getShoesTypeColor(),
                    rule.getStyleName(),
                    rule.getBodyShapeName(),
                    user.getUserID(),
                    styleList.size() < 2 ? ConstantString.SUGGEST_CLOTHES_FOR_FREE_USER_A_WEEK : ConstantString.SUGGEST_CLOTHES_FOR_FREE_USER_A_DAY,
                    userResponseStylish);

            if (outfits.size() == (styleList.size() < 2 ? ConstantString.SUGGEST_CLOTHES_FOR_FREE_USER_A_WEEK : ConstantString.SUGGEST_CLOTHES_FOR_FREE_USER_A_DAY)) {
                message = ConstantMessage.SUGGEST_FULL_CLOTHES_FOR_FREE_USER.getMessage();
            } else {
                message = ConstantMessage.SUGGEST_MISSING_OR_RUN_OUT_OF_CLOTHES_FOR_FREE_USER.getMessage();
            }

            for (List<ClothesResponse> out : outfits) {
                if (!containsOutfit(outfitList, out)) {
                    outfitList.add(out);
                }
            }

            for (List<ClothesResponse> outfit : outfitList) {
                logger.error("This is Outfit: ");
                for (ClothesResponse clothes : outfit) {
                    logger.warn("This is clothes from Outfit {}", clothes);
                }
            }

            aiStylishResponses.add(
                    AIStylishResponse
                            .builder()
                            .styleName(rule.getStyleName())
                            .bodyShapeName(rule.getBodyShapeName())
                            .outfits(outfitList)
                            .message(message)
                            .build()
            );

            if (index == ConstantString.MAXIMUM_STYLE_FOR_FREE_USER) break;
        }
        return aiStylishResponses;
    }

    public List<List<ClothesResponse>> selectUniqueOutfits(List<ClothesResponse> topInsideClothes,
                                                           List<ClothesResponse> topOutsideClothes,
                                                           List<ClothesResponse> bottomClothes,
                                                           List<ClothesResponse> shoesClothes,
                                                           List<ClothesResponse> accessoriesClothes,
                                                           String ruleTopInsideColor,
                                                           String ruleTopOutsideColor,
                                                           String ruleBottomColor,
                                                           String ruleShoesColor,
                                                           String styleName,
                                                           String bodyShapeName,
                                                           String userID,
                                                           int numberOfOutfits,
                                                           UserResponseStylish userResponseStylish) throws CustomException {

        Integer maxOutFitCanGenerate = calculateMaxOutfitsCanGenerate(topInsideClothes, topOutsideClothes, bottomClothes, shoesClothes, accessoriesClothes);
        logger.error("This is size of maxOutFitCanGenerate : {}", maxOutFitCanGenerate);
        Integer outfitInMemoryDB = 0;
        List<List<ClothesResponse>> selectedOutfits = new ArrayList<>();
        while (selectedOutfits.size() < numberOfOutfits) {

            List<ClothesResponse> outfit = new ArrayList<>();
            String topInsideID = null, topOutsideID = null, bottomKindID = null, shoesTypeID = null, accessoryKindID = null;
            outfitInMemoryDB = memoryEntityService.countMemoryByStyleAndBodyShape(styleName, bodyShapeName);

            if (outfitInMemoryDB == maxOutFitCanGenerate) break;
            if (topInsideClothes.size() > 0) {
                ClothesResponse topInside = selectRandomElement(topInsideClothes);
                topInsideID = topInside.getClothesID().toString();
                topInside.setUserResponseStylish(userResponseStylish);
                outfit.add(topInside);
            }
            if (topOutsideClothes.size() > 0) {
                ClothesResponse topOutside = selectRandomElement(topOutsideClothes);
                topOutsideID = topOutside.getClothesID().toString();
                topOutside.setUserResponseStylish(userResponseStylish);
                outfit.add(topOutside);
            }
            if (bottomClothes.size() > 0) {
                ClothesResponse bottomKind = selectRandomElement(bottomClothes);
                bottomKindID = bottomKind.getClothesID().toString();
                bottomKind.setUserResponseStylish(userResponseStylish);
                outfit.add(bottomKind);
            }
            if (shoesClothes.size() > 0) {
                ClothesResponse shoesType = selectRandomElement(shoesClothes);
                shoesTypeID = shoesType.getClothesID().toString();
                shoesType.setUserResponseStylish(userResponseStylish);
                outfit.add(shoesType);
            }
            if (accessoriesClothes.size() > 0) {
                ClothesResponse accessoryKind = selectRandomElement(accessoriesClothes);
                accessoryKindID = accessoryKind.getClothesID().toString();
                accessoryKind.setUserResponseStylish(userResponseStylish);
                outfit.add(accessoryKind);
            }

            MemoryRequest memoryRequest = MemoryRequest
                    .builder()
                    .styleName(styleName)
                    .bodyShapeName(bodyShapeName)
                    .topInsideID(topInsideID)
                    .topInsideColor(ruleTopInsideColor)
                    .topOutsideID(topOutsideID)
                    .topOutsideColor(ruleTopOutsideColor)
                    .bottomKindID(bottomKindID)
                    .bottomColor(ruleBottomColor)
                    .shoesTypeID(shoesTypeID)
                    .shoesTypeColor(ruleShoesColor)
                    .accessoryKindID(accessoryKindID)
                    .build();

            var memoryEntity = memoryEntityService.getMemoryByMemoryRequest(memoryRequest);
            if (memoryEntity != null) {
                logger.error("Memory Entity is Existed {}", memoryEntity);
            }

            // Check Outfit is existed or not when generate random outfits
            // Check Outfit which is dislike or suggest to that User or not
            if (!containsOutfit(selectedOutfits, outfit)) {
                if (memoryEntity == null) {
                    memoryRequest.setSuggestClothesToUser(userID + ",");
                    memoryEntityService.createMemoryEntity(memoryRequest);
                } else if (memoryEntity != null) {
                    if (memoryEntity.getDislikeClothesByUser() != null && memoryEntity.getDislikeClothesByUser().contains(userID)) {
                        continue;
                    }
                    if (memoryEntity.getSuggestClothesToUser() != null && memoryEntity.getSuggestClothesToUser().contains(userID)) {
                        continue;
                    }
                    memoryEntityService.updateMemoryEntityForDislikeAndSuggest(memoryEntity.getMemoryID(), userID + ",", "SUGGEST");
                }
                selectedOutfits.add(outfit);
            }
        }
        return selectedOutfits;
    }

    private Integer calculateMaxOutfitsCanGenerate(List<ClothesResponse> topInsideClothes,
                                                   List<ClothesResponse> topOutsideClothes,
                                                   List<ClothesResponse> bottomClothes,
                                                   List<ClothesResponse> shoesClothes,
                                                   List<ClothesResponse> accessoriesClothes) {
        Integer value = 1;

        if (topInsideClothes.size() > 0) value *= topInsideClothes.size();

        if (topOutsideClothes.size() > 0) value *= topOutsideClothes.size();

        if (bottomClothes.size() > 0) value *= bottomClothes.size();

        if (shoesClothes.size() > 0) value *= shoesClothes.size();

        if (accessoriesClothes.size() > 0) value *= accessoriesClothes.size();

        return value;
    }

    private ClothesResponse selectRandomElement(List<ClothesResponse> clothesList) {
        return clothesList.get(random.nextInt(clothesList.size()));
    }

    private boolean containsOutfit(List<List<ClothesResponse>> selectedOutfits, List<ClothesResponse> outfit) {
        return selectedOutfits
                .stream()
                .anyMatch(existedOutfit -> isSameOutfit(existedOutfit, outfit));
    }

    private boolean isSameOutfit(List<ClothesResponse> outfit1, List<ClothesResponse> outfit2) {
        if (outfit1.size() != outfit2.size()) {
            return false;
        }
        for (ClothesResponse item : outfit1) {
            if (!outfit2.contains(item)) {
                return false;
            }
        }
        return true;
    }

    private List<Style> selectRandomPremiumStyle(List<Style> styleList)
    {
        List<Style> styles = new ArrayList<>();
        while(styles.size() < ConstantString.MAXIMUM_STYLE_FOR_PREMIUM_USER_PER_WEEK)
        {
            Style style = styleList.get(random.nextInt(styleList.size()));
            if(!styles.contains(style)) styles.add(style);
        }
        return styles;
    }

    private List<Integer> calculateNumberOfStyleAppearDuringWeek(List<Style> styleList)
    {
        List<Integer> list = new ArrayList<>();
        int days = ConstantString.MAXIMUM_STYLE_FOR_PREMIUM_USER_PER_WEEK;
        int style = styleList.size();
        int remainder = 0;
        for(int i = 0; i < style; ++i)
        {
            remainder = days / (style - i);
            days -= remainder;
            list.add(remainder);
        }
        return list;
    }
    private List<AIStylishResponse> suggestClothesForPremiumUser(User user, BodyShape bodyShape, List<Style> styleList,  UserResponseStylish userResponseStylish) throws CustomException{

        List<Style> selectedStyle = null;
        List<Integer> calculateDaysForEachStyle = null;
        if(styleList.size() > ConstantString.MAXIMUM_STYLE_FOR_PREMIUM_USER_PER_WEEK) selectedStyle = selectRandomPremiumStyle(styleList);
        else
        {
            selectedStyle = new ArrayList<>(styleList);
            calculateDaysForEachStyle = calculateNumberOfStyleAppearDuringWeek(styleList);
        }

        List<RuleMatchingClothesResponse> ruleMatchingClothesResponses = selectedStyle
                .stream()
                .map(style -> ruleMatchingClothesService.getRuleMatchingClothesByStyleAndBodyShape(style.getStyleID(), bodyShape.getBodyShapeID()))
                .collect(Collectors.toList());

        List<AIStylishResponse> aiStylishResponses = new ArrayList<>();

        for (int i = 0; i < ruleMatchingClothesResponses.size(); ++i) {

            RuleMatchingClothesResponse rule = ruleMatchingClothesResponses.get(i);

            List<List<ClothesResponse>> outfitList = new ArrayList<>();

            List<ClothesResponse> topInsideClothes = clothesService.getClothesBaseOnTypeOfClothesAndColorOrMaterials(
                    rule.getTopInside(), rule.getTopInsideColor(), rule.getTopMaterial());

            for (ClothesResponse clothesResponse : topInsideClothes) {
                logger.warn("This is Top Inside Clothes {}", clothesResponse);
            }

            List<ClothesResponse> topOutsideClothes = clothesService.getClothesBaseOnTypeOfClothesAndColorOrMaterials(
                    rule.getTopOutside(), rule.getTopOutsideColor(), rule.getTopMaterial());

            for (ClothesResponse clothesResponse : topOutsideClothes) {
                logger.warn("This is Top Outside Clothes {}", clothesResponse);
            }

            List<ClothesResponse> bottomClothes = clothesService.getClothesBaseOnTypeOfClothesAndColorOrMaterials(
                    rule.getBottomKind(), rule.getBottomColor(), rule.getBottomMaterial());

            for (ClothesResponse clothesResponse : bottomClothes) {
                logger.warn("This is Bottom Clothes {}", clothesResponse);
            }

            List<ClothesResponse> shoesClothes = clothesService.getClothesBaseOnTypeOfClothesAndColorOrMaterials(
                    rule.getShoesType(), rule.getShoesTypeColor(), rule.getBottomMaterial());

            for (ClothesResponse clothesResponse : shoesClothes) {
                logger.warn("This is Shoes Clothes {}", clothesResponse);
            }

            List<ClothesResponse> accessoriesClothes = clothesService.getClothesBaseOnTypeOfClothesAndMaterial(
                    rule.getAccessoryKind(), rule.getAccessoryMaterial());

            for (ClothesResponse clothesResponse : accessoriesClothes) {
                logger.warn("This is Accessories Clothes {}", clothesResponse);
            }
            String message = "";
            if(styleList.size() == ConstantString.PREMIUM_HAVE_ONLY_ONE_STYLE)
            {
                outfitList = selectUniqueOutfits(
                        topInsideClothes,
                        topOutsideClothes,
                        bottomClothes,
                        shoesClothes,
                        accessoriesClothes,
                        rule.getTopInsideColor(),
                        rule.getTopOutsideColor(),
                        rule.getBottomColor(),
                        rule.getShoesTypeColor(),
                        rule.getStyleName(),
                        rule.getBodyShapeName(),
                        user.getUserID(),
                        ConstantString.SUGGEST_CLOTHES_FOR_PREMIUM_USER_HAVING_ONE_STYLE,
                        userResponseStylish);

                if(outfitList.size() == ConstantString.SUGGEST_CLOTHES_FOR_PREMIUM_USER_HAVING_ONE_STYLE)
                {
                    message = ConstantMessage.SUGGEST_FULL_CLOTHES_FOR_PREMIUM_USER.getMessage();
                }
                else message = ConstantMessage.SUGGEST_MISSING_OR_RUN_OUT_OF_CLOTHES_FOR_PREMIUM_USER.getMessage();
            }
            else if(styleList.size() > ConstantString.MAXIMUM_STYLE_FOR_PREMIUM_USER_PER_WEEK)
            {
                List<List<ClothesResponse>> outfits = selectUniqueOutfits(
                        topInsideClothes,
                        topOutsideClothes,
                        bottomClothes,
                        shoesClothes,
                        accessoriesClothes,
                        rule.getTopInsideColor(),
                        rule.getTopOutsideColor(),
                        rule.getBottomColor(),
                        rule.getShoesTypeColor(),
                        rule.getStyleName(),
                        rule.getBodyShapeName(),
                        user.getUserID(),
                        ConstantString.SUGGEST_CLOTHES_FOR_PREMIUM_USER_A_DAY,
                        userResponseStylish);

                for (List<ClothesResponse> out : outfits) {
                    if (!containsOutfit(outfitList, out)) {
                        outfitList.add(out);
                    }
                }

                for (List<ClothesResponse> outfit : outfitList) {
                    logger.error("This is Outfit: ");
                    for (ClothesResponse clothes : outfit) {
                        logger.warn("This is clothes from Outfit {}", clothes);
                    }
                }
                if(outfitList.size() == ConstantString.SUGGEST_CLOTHES_FOR_PREMIUM_USER_A_DAY)
                {
                    message = ConstantMessage.SUGGEST_FULL_CLOTHES_FOR_PREMIUM_USER.getMessage();
                }
                else message = ConstantMessage.SUGGEST_MISSING_OR_RUN_OUT_OF_CLOTHES_FOR_PREMIUM_USER.getMessage();
            }
            else {
                int index = 0;
                for(int j = 0; j < calculateDaysForEachStyle.get(i); j++)
                {
                    List<List<ClothesResponse>> outfits = selectUniqueOutfits(
                            topInsideClothes,
                            topOutsideClothes,
                            bottomClothes,
                            shoesClothes,
                            accessoriesClothes,
                            rule.getTopInsideColor(),
                            rule.getTopOutsideColor(),
                            rule.getBottomColor(),
                            rule.getShoesTypeColor(),
                            rule.getStyleName(),
                            rule.getBodyShapeName(),
                            user.getUserID(),
                            ConstantString.SUGGEST_CLOTHES_FOR_PREMIUM_USER_A_DAY,
                            userResponseStylish);

                    index += outfits.size();

                    for (List<ClothesResponse> out : outfits) {
                        if (!containsOutfit(outfitList, out)) {
                            outfitList.add(out);
                        }
                    }
                }

                if(index == ConstantString.SUGGEST_CLOTHES_FOR_PREMIUM_USER_A_DAY * calculateDaysForEachStyle.get(i))
                {
                    message = ConstantMessage.SUGGEST_FULL_CLOTHES_FOR_PREMIUM_USER.getMessage();
                }
                else message = ConstantMessage.SUGGEST_MISSING_OR_RUN_OUT_OF_CLOTHES_FOR_PREMIUM_USER.getMessage();

                for (List<ClothesResponse> outfit : outfitList) {
                    logger.error("This is Outfit: ");
                    for (ClothesResponse clothes : outfit) {
                        logger.warn("This is clothes from Outfit {}", clothes);
                    }
                }
            }

            aiStylishResponses.add(
                    AIStylishResponse
                            .builder()
                            .styleName(rule.getStyleName())
                            .bodyShapeName(rule.getBodyShapeName())
                            .outfits(outfitList)
                            .message(message)
                            .build()
            );
        }
        return aiStylishResponses;
    }

    @Override
    public AIStylishResponse createNewClothesAfterRejectClothesForPremiumUser(RejectClothesRequest rejectClothesRequest) throws CustomException {
        Style style = styleService.getStyleByStyleName(rejectClothesRequest.getStyleName());
        if(style == null)
        {
            throw new CustomException(ConstantMessage.STYLE_NAME_IS_NOT_EXISTED.getMessage());
        }

        BodyShape bodyShape = bodyShapeService.getBodyShapeByBodyShapeName(rejectClothesRequest.getBodyShapeName());
        if(bodyShape == null)
        {
            throw new CustomException(ConstantMessage.BODY_SHAPE_NAME_IS_NOT_EXISTED.getMessage());
        }

        // Check ID User or User Entity is exist or not
        Optional.of(rejectClothesRequest.getUserID())
                .filter(id -> !id.isEmpty() && !id.isBlank())
                .orElseThrow(() -> new CustomException(ConstantMessage.USERID_IS_EMPTY_OR_NOT_EXIST.getMessage()));

        User user = Optional.ofNullable(userService.getUserEntityByUserID(rejectClothesRequest.getUserID()))
                .orElseThrow(() -> new CustomException(ConstantMessage.CANNOT_FIND_USER_BY_USERID.getMessage()));

        UserResponseStylish userResponseStylish = UserResponseStylish
                .builder()
                .userID(user.getUserID())
                .username(user.getUsername())
                .imgUrl(user.getImgUrl())
                .build();
        RuleMatchingClothesResponse rule = ruleMatchingClothesService.getRuleMatchingClothesByStyleAndBodyShape(style.getStyleID(), bodyShape.getBodyShapeID());

        List<List<ClothesResponse>> outfitList = new ArrayList<>();

        List<ClothesResponse> topInsideClothes = clothesService.getClothesBaseOnTypeOfClothesAndColorOrMaterials(
                rule.getTopInside(), rule.getTopInsideColor(), rule.getTopMaterial());

        for (ClothesResponse clothesResponse : topInsideClothes) {
            logger.warn("This is Top Inside Clothes {}", clothesResponse);
        }

        List<ClothesResponse> topOutsideClothes = clothesService.getClothesBaseOnTypeOfClothesAndColorOrMaterials(
                rule.getTopOutside(), rule.getTopOutsideColor(), rule.getTopMaterial());

        for (ClothesResponse clothesResponse : topOutsideClothes) {
            logger.warn("This is Top Outside Clothes {}", clothesResponse);
        }

        List<ClothesResponse> bottomClothes = clothesService.getClothesBaseOnTypeOfClothesAndColorOrMaterials(
                rule.getBottomKind(), rule.getBottomColor(), rule.getBottomMaterial());

        for (ClothesResponse clothesResponse : bottomClothes) {
            logger.warn("This is Bottom Clothes {}", clothesResponse);
        }

        List<ClothesResponse> shoesClothes = clothesService.getClothesBaseOnTypeOfClothesAndColorOrMaterials(
                rule.getShoesType(), rule.getShoesTypeColor(), rule.getBottomMaterial());

        for (ClothesResponse clothesResponse : shoesClothes) {
            logger.warn("This is Shoes Clothes {}", clothesResponse);
        }

        List<ClothesResponse> accessoriesClothes = clothesService.getClothesBaseOnTypeOfClothesAndMaterial(
                rule.getAccessoryKind(), rule.getAccessoryMaterial());

        for (ClothesResponse clothesResponse : accessoriesClothes) {
            logger.warn("This is Accessories Clothes {}", clothesResponse);
        }

        MemoryEntity memoryEntity = memoryEntityService.getMemoryForRejectClothesRequest(rejectClothesRequest);
        memoryEntityService.updateMemoryEntityForDislikeAndSuggest(memoryEntity.getMemoryID(), rejectClothesRequest.getUserID() + ",", "DISLIKE");

        outfitList = selectUniqueOutfits(
                topInsideClothes,
                topOutsideClothes,
                bottomClothes,
                shoesClothes,
                accessoriesClothes,
                rule.getTopInsideColor(),
                rule.getTopOutsideColor(),
                rule.getBottomColor(),
                rule.getShoesTypeColor(),
                rule.getStyleName(),
                rule.getBodyShapeName(),
                rejectClothesRequest.getUserID(),
                ConstantString.SUGGEST_CLOTHES_FOR_PREMIUM_USER_AFTER_REJECT,
                userResponseStylish);

        String message = "";
        if(outfitList.size() == ConstantString.SUGGEST_CLOTHES_FOR_PREMIUM_USER_AFTER_REJECT)
        {
            message = ConstantMessage.SUGGEST_FULL_CLOTHES_FOR_PREMIUM_USER.getMessage();
        }
        else message = ConstantMessage.SUGGEST_MISSING_OR_RUN_OUT_OF_CLOTHES_FOR_PREMIUM_USER.getMessage();

        return AIStylishResponse
                .builder()
                .styleName(rule.getStyleName())
                .bodyShapeName(rule.getBodyShapeName())
                .outfits(outfitList)
                .message(message)
                .build();
    }

    @Override
    public List<AIStylishResponse> selectChoiceWhenRunOutOfOutfitsForPremium(SuggestChoiceForPremiumUser suggestChoiceForPremiumUser) throws CustomException {
        Style style = styleService.getStyleByStyleName(suggestChoiceForPremiumUser.getStyleName());
        if(style == null)
        {
            throw new CustomException(ConstantMessage.STYLE_NAME_IS_NOT_EXISTED.getMessage());
        }

        BodyShape bodyShape = bodyShapeService.getBodyShapeByBodyShapeName(suggestChoiceForPremiumUser.getBodyShapeName());
        if(bodyShape == null)
        {
            throw new CustomException(ConstantMessage.BODY_SHAPE_NAME_IS_NOT_EXISTED.getMessage());
        }

        // Check ID User or User Entity is exist or not
        Optional.of(suggestChoiceForPremiumUser.getUserID())
                .filter(id -> !id.isEmpty() && !id.isBlank())
                .orElseThrow(() -> new CustomException(ConstantMessage.USERID_IS_EMPTY_OR_NOT_EXIST.getMessage()));

        User user = Optional.ofNullable(userService.getUserEntityByUserID(suggestChoiceForPremiumUser.getUserID()))
                .orElseThrow(() -> new CustomException(ConstantMessage.CANNOT_FIND_USER_BY_USERID.getMessage()));

        if(suggestChoiceForPremiumUser.getChoice().toUpperCase().replace(' ', '_').equals(ConstantString.CHANGE_TO_ANOTHER_STYLE))
        {
            return new ArrayList<>();
        }

        return null;
    }
}
