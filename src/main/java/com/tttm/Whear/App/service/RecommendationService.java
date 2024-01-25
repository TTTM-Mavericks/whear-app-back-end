package com.tttm.Whear.App.service;

import com.tttm.Whear.App.dto.PairConsineSimilarity;
import com.tttm.Whear.App.dto.Pairs;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.utils.request.ClothesRequest;
import com.tttm.Whear.App.utils.request.HistorySearchRequest;
import com.tttm.Whear.App.utils.response.ClothesResponse;

import java.util.List;

public interface RecommendationService {
      List<ClothesResponse> getListRecommendationByUserHistoryItems(String userID) throws CustomException;
}
