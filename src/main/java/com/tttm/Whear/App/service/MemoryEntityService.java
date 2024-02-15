package com.tttm.Whear.App.service;

import com.tttm.Whear.App.entity.MemoryEntity;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.utils.request.MemoryRequest;

public interface MemoryEntityService {
    void createMemoryEntity(MemoryRequest memoryRequest) throws CustomException;

    MemoryEntity getMemoryByMemoryRequest(MemoryRequest memoryRequest) throws CustomException;

    void updateMemoryEntityForDislikeAndSuggest(Integer memoryID, String userID, String keyword) throws CustomException;

    Integer countMemoryByStyleAndBodyShape(String styleName, String bodyShapeName) throws CustomException;
}
