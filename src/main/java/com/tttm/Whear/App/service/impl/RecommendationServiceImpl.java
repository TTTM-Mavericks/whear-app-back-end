package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.constant.ConstantMessage;
import com.tttm.Whear.App.dto.ClothesItemDto;
import com.tttm.Whear.App.dto.PairConsineSimilarity;
import com.tttm.Whear.App.dto.Pairs;
import com.tttm.Whear.App.entity.User;
import com.tttm.Whear.App.enums.*;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.service.*;
import com.tttm.Whear.App.utils.request.HistorySearchRequest;
import com.tttm.Whear.App.utils.response.ClothesResponse;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.C;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {
    private static final double THRESHOLD = 0.55555555;

    private final UserService userService;

    private final HistoryService historyService;

    private final ClothesService clothesService;

    @Override
    public List<ClothesResponse> getListRecommendationByUserHistoryItems(String userID) throws CustomException
    {
        Optional.of(userID)
                .filter(id -> !id.isEmpty() && !id.isBlank())
                .orElseThrow(() -> new CustomException(ConstantMessage.USERID_IS_EMPTY_OR_NOT_EXIST.getMessage()));

        User user = userService.getUserEntityByUserID(userID);
        if(user == null)
        {
            throw new CustomException(ConstantMessage.CANNOT_FIND_USER_BY_USERID.getMessage());
        }

        String userSearchText = convertListHistoryItemsToText(userID);
        List<Pairs> clothesItemList = convertListClothesToListClothesPairs();

        List<PairConsineSimilarity> listConsineSimilarity = calculateConsineSimilarities(userSearchText, clothesItemList);

        Collections.sort(listConsineSimilarity, Comparator.comparingDouble(PairConsineSimilarity::getConsineSimilarity).reversed());
        List<ClothesResponse> clothesResponses = listConsineSimilarity.stream()
                .map(cloth -> {
                    try {
                        return clothesService.getClothesByID(cloth.getClothesID());
                    } catch (CustomException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        for(PairConsineSimilarity pairs : listConsineSimilarity)
        {
            System.out.println(clothesService.getClothesByID(pairs.getClothesID()) + " " +  pairs.getConsineSimilarity());
        }
        return clothesResponses;
    }

    public List<PairConsineSimilarity> calculateConsineSimilarities(String userSearchText, List<Pairs> listClothesText)
    {
        List<PairConsineSimilarity> listConsineSimilarity = new ArrayList<>();
        Map<String, Integer> userHistorySearchVector = createFequencyAppearanceVector(userSearchText);
        for(Pairs Items : listClothesText)
        {
            Map<String, Integer> clothesItemVector = createFequencyAppearanceVector(Items.getClothesItem());
            double consineSimilarity = calculateCosineSimilarity(userHistorySearchVector, clothesItemVector);
            listConsineSimilarity.add(new PairConsineSimilarity(Items.getClothesID(), consineSimilarity));
        }
        return listConsineSimilarity;
    }

    private Map<String, Integer> createFequencyAppearanceVector(String text)
    {
        Map<String, Integer> map = new HashMap<>();
        String[] listKeyWords = text.toUpperCase().split("\\s+");
        for(String keywords : listKeyWords)
        {
            map.put(keywords, map.getOrDefault(keywords, 0) + 1);
        }
        return map;
    }

    private Double calculateCosineSimilarity(Map<String, Integer> userVector, Map<String, Integer> clothesVector)
    {
        double dotClothes = 0.0, magnitudeOfUser = 0.0, magnitudeOfClothes = 0.0;
        for(String keyWords : userVector.keySet())
        {
            if(clothesVector.containsKey(keyWords)) // Check whether the keywords is appear between UserHistorySearch and ProductList
            {
                dotClothes += userVector.get(keyWords) * clothesVector.get(keyWords);
            }
            magnitudeOfUser += Math.pow(userVector.get(keyWords), 2);
        }

        for(int value : clothesVector.values())
        {
            magnitudeOfClothes += Math.pow(value, 2);
        }

        return dotClothes / (Math.sqrt(magnitudeOfUser) * Math.sqrt(magnitudeOfClothes));
    }

    private String convertListHistoryItemsToText(String userID) throws CustomException
    {
        List<String> historyItems = historyService.getAllHistoryItemsByCustomerID(userID).getHistoryItems()
                .stream()
                .map(items -> items.toUpperCase())
                .collect(Collectors.toList());
        return String.join(" ", historyItems);
    }

    private List<Pairs> convertListClothesToListClothesPairs()
    {
        List<ClothesItemDto> ClothesItemDtoList = clothesService
                .getAllClothes()
                .stream()
                .map(this::convertToClothesItemDto)
                .toList();

        List<Pairs> clothesItemList = new ArrayList<>();
        for(int i = 0; i < ClothesItemDtoList.size(); i++)
        {
            ClothesItemDto clothes = ClothesItemDtoList.get(i);
            String ClotheItems =  clothes.getNameOfProduct().toUpperCase() + " " + clothes.getTypeOfClothes() + " " + clothes.getShape() + " " +
                    clothes.getMaterials() + " " + clothes.seasonToString() + " " +  clothes.sizeToString() + " " + clothes.colorToString();
            clothesItemList.add(new Pairs(ClothesItemDtoList.get(i).getClothesID(), ClotheItems));
        }
        return clothesItemList;
    }

    private ClothesItemDto convertToClothesItemDto(ClothesResponse clothesResponse)
    {
        return ClothesItemDto
                .builder()
                .clothesID(clothesResponse.getClothesID())
                .nameOfProduct(clothesResponse.getNameOfProduct())
                .typeOfClothes(ClothesType.valueOf(clothesResponse.getTypeOfClothes()))
                .shape(ShapeType.valueOf(clothesResponse.getShape()))
                .materials(MaterialType.valueOf(clothesResponse.getMaterials()))
                .seasons(
                        clothesResponse
                                .getClothesSeasons()
                                .stream()
                                .map(Season -> SeasonType.valueOf(Season))
                                .toList()
                )
                .sizes(
                        clothesResponse
                                .getClothesSizes()
                                .stream()
                                .map(Size -> SizeType.valueOf(Size))
                                .toList()
                )
                .colors(
                        clothesResponse
                                .getClothesColors()
                                .stream()
                                .map(Colors -> ColorType.valueOf(Colors))
                                .toList()
                )
                .build();
    }
}
