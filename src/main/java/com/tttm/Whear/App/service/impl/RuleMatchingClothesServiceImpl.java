package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.constant.ConstantMessage;
import com.tttm.Whear.App.entity.RuleMatchingClothes;
import com.tttm.Whear.App.enums.*;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.repository.RuleMatchingClothesRepository;
import com.tttm.Whear.App.service.RuleMatchingClothesService;
import com.tttm.Whear.App.utils.request.RuleMatchingClothesRequest;
import com.tttm.Whear.App.utils.response.RuleMatchingClothesResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RuleMatchingClothesServiceImpl implements RuleMatchingClothesService {
    private final RuleMatchingClothesRepository repository;
    private final Logger logger = LoggerFactory.getLogger(RuleMatchingClothesServiceImpl.class);
    private <T extends Enum<T>> void validateEnum(Class<T> enumClass, String fieldValue) {
        try {
            if (fieldValue != null) {
                Enum.valueOf(enumClass, fieldValue);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value: '" + fieldValue + " value from " + enumClass.getSimpleName() + " enum.");
        }
    }

    @Override
    public RuleMatchingClothesRequest createNewRule(RuleMatchingClothesRequest request) throws CustomException{
        validateEnum(StyleType.class, request.getStyleType());
        validateEnum(BodyShapeType.class, request.getBodyShapeType());
//        validateEnum(ClothesType.class, request.getTopInside());
//        validateEnum(ColorType.class, request.getTopInsideColor());
//        validateEnum(ClothesType.class, request.getTopOutside());
//        validateEnum(ColorType.class, request.getTopOutsideColor());
//        validateEnum(ClothesMaterialType.class, request.getTopMaterial());
//        validateEnum(ClothesType.class, request.getBottomKind());
//        validateEnum(ColorType.class, request.getBottomColor());
//        validateEnum(ShoesType.class, request.getShoesType());
//        validateEnum(ColorType.class, request.getShoesTypeColor());
//        validateEnum(ClothesMaterialType.class, request.getBottomMaterial());
//        validateEnum(AccessoriesType.class, request.getAccessoryKind());
//        validateEnum(AccessoriesMaterialType.class, request.getAccessoryMaterial());

        repository.createNewRuleMatchingClothes(
                request.getStyleType().toUpperCase(),
                request.getBodyShapeType().toUpperCase(),
                request.getTopInside().toUpperCase(),
                request.getTopInsideColor().toUpperCase(),
                request.getTopOutside().toUpperCase(),
                request.getTopOutsideColor().toUpperCase(),
                request.getTopMaterial().toUpperCase(),
                request.getBottomKind().toUpperCase(),
                request.getBottomColor().toUpperCase(),
                request.getShoesType().toUpperCase(),
                request.getShoesTypeColor().toUpperCase(),
                request.getBottomMaterial().toUpperCase(),
                request.getAccessoryKind().toUpperCase(),
                request.getAccessoryMaterial().toUpperCase()
        );

        return RuleMatchingClothesRequest.builder()
                .styleType(request.getStyleType().toUpperCase())
                .bodyShapeType(request.getBodyShapeType().toUpperCase())
                .topInside(request.getTopInside().toUpperCase())
                .topInsideColor(request.getTopInsideColor().toUpperCase())
                .topOutside(request.getTopOutside().toUpperCase())
                .topOutsideColor(request.getTopOutsideColor().toUpperCase())
                .topMaterial(request.getTopMaterial().toUpperCase())
                .bottomKind(request.getBottomKind().toUpperCase())
                .bottomColor(request.getBottomColor().toUpperCase())
                .shoesType(request.getShoesType().toUpperCase())
                .shoesTypeColor(request.getShoesTypeColor().toUpperCase())
                .bottomMaterial(request.getBottomMaterial().toUpperCase())
                .accessoryKind(request.getAccessoryKind().toUpperCase())
                .accessoryMaterial(request.getAccessoryMaterial().toUpperCase())
                .build();

    }

    @Override
    public List<RuleMatchingClothesResponse> getAllRuleMatchingClothes() {
        return repository
                .findAll()
                .stream()
                .map(this::convertToRuleMatchingClothesResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RuleMatchingClothesResponse getRuleMatchingClothesByRuleID(Integer ruleID) throws CustomException {
        return repository
                .findById(ruleID)
                .filter(r -> ruleID >= 0)
                .map(this::convertToRuleMatchingClothesResponse)
                .orElseThrow(() -> {
                    logger.warn(ConstantMessage.ID_IS_EMPTY_OR_NOT_EXIST.getMessage());
                    return new CustomException(ConstantMessage.ID_IS_EMPTY_OR_NOT_EXIST.getMessage());
                });
    }

    private RuleMatchingClothesResponse convertToRuleMatchingClothesResponse(RuleMatchingClothes ruleMatchingClothes) {
        return RuleMatchingClothesResponse.builder()
                //.styleType(ruleMatchingClothes.getStyleType().toString())
                //.bodyShapeType(ruleMatchingClothes.getBodyShapeType().toString())
                .topInside(ruleMatchingClothes.getTopInside().toString())
                .topInsideColor(ruleMatchingClothes.getTopInsideColor().toString())
                .topOutside(ruleMatchingClothes.getTopOutside().toString())
                .topOutsideColor(ruleMatchingClothes.getTopOutsideColor().toString())
                .topMaterial(ruleMatchingClothes.getTopMaterial().toString())
                .bottomKind(ruleMatchingClothes.getBottomKind().toString())
                .bottomColor(ruleMatchingClothes.getBottomColor().toString())
                .shoesType(ruleMatchingClothes.getShoesType().toString())
                .shoesTypeColor(ruleMatchingClothes.getShoesTypeColor().toString())
                .bottomMaterial(ruleMatchingClothes.getBottomMaterial().toString())
                .accessoryKind(ruleMatchingClothes.getAccessoryKind().toString())
                .accessoryMaterial(ruleMatchingClothes.getAccessoryMaterial().toString())
                .build();
    }
}
