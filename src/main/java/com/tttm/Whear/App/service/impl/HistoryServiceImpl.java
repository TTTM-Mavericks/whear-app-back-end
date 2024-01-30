package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.constant.ConstantMessage;
import com.tttm.Whear.App.entity.History;
import com.tttm.Whear.App.entity.User;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.repository.HistoryRepository;
import com.tttm.Whear.App.service.ClothesService;
import com.tttm.Whear.App.service.HistoryService;
import com.tttm.Whear.App.service.UserService;
import com.tttm.Whear.App.utils.request.HistoryRequest;
import com.tttm.Whear.App.utils.response.ClothesResponse;
import com.tttm.Whear.App.utils.response.HistoryResponse;
import com.tttm.Whear.App.utils.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;

    private final UserService userService;

    private final ClothesService clothesService;

    @Override
    public void createHistoryItem(HistoryRequest historyRequest) throws CustomException {
        Optional.of(historyRequest.getCustomerID())
                .filter(id -> !id.isEmpty() && !id.isBlank())
                .orElseThrow(() -> new CustomException(ConstantMessage.USERID_IS_EMPTY_OR_NOT_EXIST.getMessage()));

        User user = userService.getUserEntityByUserID(historyRequest.getCustomerID());
        if (user == null) {
            throw new CustomException(ConstantMessage.CANNOT_FIND_USER_BY_USERID.getMessage());
        }

        historyRequest
                .getHistoryItems()
                .forEach(historyItem -> historyRepository
                        .createHistoryItem(historyRequest.getCustomerID(),
                                historyItem.toUpperCase())
                );
    }

    @Override
    public List<String> createHistoryItemBasedOnReactFeature(String userID, Integer clothesID) throws CustomException {
        ClothesResponse clothesResponse = clothesService.getClothesByID(clothesID);

        List<String> historyReact = Stream.of(
                clothesResponse.getNameOfProduct() + " " + clothesID,
                clothesResponse.getTypeOfClothes() + " " + clothesID,
                clothesResponse.getShape() + " " + clothesID,
                clothesResponse.getMaterials() + " " + clothesID)
                .collect(Collectors.toList());

        clothesResponse.getClothesSeasons()
                .forEach(
                        season -> historyReact.add(season + " " + clothesID)
                );
        clothesResponse.getClothesColors()
                .forEach(
                        color -> historyReact.add(color + " " + clothesID)
                );
        clothesResponse.getClothesSizes()
                .forEach(
                        size -> historyReact.add(size + " " + clothesID)
                );

        historyReact.forEach(history -> historyRepository.createHistoryItem(userID, history));

        return historyReact;
    }

    @Override
    public void deleteHistoryItemBasedOnReactFeature(String userID, Integer clothesID) throws CustomException {
        List<String> histories = createHistoryItemBasedOnReactFeature(userID, clothesID);
        histories.forEach(history -> historyRepository.deleteAllHistoryItems(userID, history));
    }

    @Override
    public HistoryResponse getAllHistoryItemsByCustomerID(String customerID) throws CustomException {
        List<String> historyItems = historyRepository
                .getAllHistoryItemsByCustomerID(customerID)
                .stream()
                .map(history -> history.getHistoryItem())
                .toList();

        return convertToHistoryResponse(customerID, historyItems);
    }

    public HistoryResponse convertToHistoryResponse(String customerID, List<String> historyItem) {
        return HistoryResponse
                .builder()
                .customerID(customerID)
                .historyItems(historyItem)
                .build();

    }
}
