package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.constant.ConstantMessage;
import com.tttm.Whear.App.constant.ConstantString;
import com.tttm.Whear.App.entity.BodyShape;
import com.tttm.Whear.App.entity.Customer;
import com.tttm.Whear.App.entity.Style;
import com.tttm.Whear.App.entity.User;
import com.tttm.Whear.App.enums.ESubRole;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.service.*;
import com.tttm.Whear.App.utils.response.AIStylishResponse;
import com.tttm.Whear.App.utils.response.ClothesResponse;
import com.tttm.Whear.App.utils.response.RuleMatchingClothesResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static com.tttm.Whear.App.enums.ESubRole.*;

@Service
@RequiredArgsConstructor
public class AIStylishServiceImpl implements AIStylishService {
    private final UserService userService;
    private final ClothesService clothesService;
    private final CustomerService customerService;
    private final RuleMatchingClothesService ruleMatchingClothesService;
    private final SubroleService subroleService;
    private final StyleService styleService;
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

        switch (subroleService.getSubroleByID(customer.getSubRoleID()).getSubRoleName()) {
            case LV1:
                outfitsForWeek = suggestClothesForFreeUser(user, bodyShape, styleList);
                break;
            case LV2:
                outfitsForWeek = suggestClothesForPremiumUser(user, bodyShape, styleList);
                break;
        }
        return outfitsForWeek;
    }

    private List<AIStylishResponse> suggestClothesForFreeUser(User user, BodyShape bodyShape, List<Style> styleList) {
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

            List<ClothesResponse> topOutsideClothes = clothesService.getClothesBaseOnTypeOfClothesAndColorOrMaterials(
                    rule.getTopOutside(), rule.getTopOutsideColor(), rule.getTopMaterial());

            List<ClothesResponse> bottomClothes = clothesService.getClothesBaseOnTypeOfClothesAndColorOrMaterials(
                    rule.getBottomKind(), rule.getBottomColor(), rule.getBottomMaterial());

            List<ClothesResponse> shoesClothes = clothesService.getClothesBaseOnTypeOfClothesAndColorOrMaterials(
                    rule.getShoesType(), rule.getShoesTypeColor(), rule.getBottomMaterial());

            List<ClothesResponse> accessoriesClothes = clothesService.getClothesBaseOnTypeOfClothesAndMaterial(
                    rule.getAccessoryKind(), rule.getAccessoryMaterial());
            index++;
            // Check If Free User only have 1 Style so that recommend 4 outfits for 2 days / week
            if(styleList.size() < 2)
            {
                outfitList = selectUniqueOutfits(topInsideClothes, topOutsideClothes, bottomClothes,
                        shoesClothes, accessoriesClothes, ConstantString.SUGGEST_CLOTHES_FOR_FREE_USER_A_WEEK);

                aiStylishResponses.add(
                        AIStylishResponse
                                .builder()
                                .styleName(rule.getStyleName())
                                .bodyShapeName(rule.getBodyShapeName())
                                .outfits(outfitList)
                                .build()
                );
            }
            else// Check If Free User have more than 1 Style so that recommend 2 outfits a day with 2 days / week
            {
                List<List<ClothesResponse>> outfits = selectUniqueOutfits(topInsideClothes, topOutsideClothes, bottomClothes,
                        shoesClothes, accessoriesClothes, ConstantString.SUGGEST_CLOTHES_FOR_FREE_USER_A_DAY);
                for (List<ClothesResponse> out : outfits)
                {
                    if (!containsOutfit(outfitList, out)) {
                        outfitList.add(out);
                    }
                }
                aiStylishResponses.add(
                        AIStylishResponse
                                .builder()
                                .styleName(rule.getStyleName())
                                .bodyShapeName(rule.getBodyShapeName())
                                .outfits(outfitList)
                                .build()
                );
                if(index == ConstantString.MAXIMUM_STYLE_FOR_FREE_USER) break;
            }
        }
        return aiStylishResponses;
    }

    public List<List<ClothesResponse>> selectUniqueOutfits(List<ClothesResponse> topInsideClothes,
                                                           List<ClothesResponse> topOutsideClothes,
                                                           List<ClothesResponse> bottomClothes,
                                                           List<ClothesResponse> shoesClothes,
                                                           List<ClothesResponse> accessoriesClothes,
                                                           int numberOfOutfits) {
        List<List<ClothesResponse>> selectedOutfits = new ArrayList<>();
        while (selectedOutfits.size() < numberOfOutfits) {
            List<ClothesResponse> outfit = new ArrayList<>();
            if(topInsideClothes.size() > 0) outfit.add(selectRandomElement(topInsideClothes));
            if(topOutsideClothes.size() > 0) outfit.add(selectRandomElement(topOutsideClothes));
            if(bottomClothes.size() > 0) outfit.add(selectRandomElement(bottomClothes));
            if(shoesClothes.size() > 0) outfit.add(selectRandomElement(shoesClothes));
            if(accessoriesClothes.size() > 0) outfit.add(selectRandomElement(accessoriesClothes));

            if (!containsOutfit(selectedOutfits, outfit)) {
                selectedOutfits.add(outfit);
            }
        }
        return selectedOutfits;
    }

    private ClothesResponse selectRandomElement(List<ClothesResponse> clothesList) {
        int randomIndex = random.nextInt(clothesList.size());
        return clothesList.get(randomIndex);
    }

    private boolean containsOutfit(List<List<ClothesResponse>> selectedOutfits, List<ClothesResponse> outfit) {
        for (List<ClothesResponse> existingOutfit : selectedOutfits) {
            if (isSameOutfit(existingOutfit, outfit)) {
                return true;
            }
        }
        return false;
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

    private List<AIStylishResponse> suggestClothesForPremiumUser(User user, BodyShape bodyShape, List<Style> styleList) {
        return null;

    }
}
