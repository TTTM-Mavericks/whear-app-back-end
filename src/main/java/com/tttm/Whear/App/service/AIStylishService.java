package com.tttm.Whear.App.service;

import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.utils.response.AIStylishResponse;
import com.tttm.Whear.App.utils.response.ClothesResponse;

import java.util.List;

public interface AIStylishService {
    List<AIStylishResponse> getSuggestClothesForUser(String userID) throws CustomException;
}
