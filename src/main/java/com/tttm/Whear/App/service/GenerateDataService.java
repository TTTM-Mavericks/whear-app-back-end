package com.tttm.Whear.App.service;

import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.utils.request.ClothesRequest;
import com.tttm.Whear.App.utils.response.ClothesResponse;

import java.util.List;

public interface GenerateDataService {
    List<ClothesRequest> generateRandomListClothes(int size) throws CustomException;
    List<String> generateRandomHistoryUserSearch(int size) throws CustomException;
}
