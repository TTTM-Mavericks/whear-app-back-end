package com.tttm.Whear.App.service;

import com.tttm.Whear.App.entity.Style;
import com.tttm.Whear.App.exception.CustomException;

import java.util.List;

public interface StyleService {
    Style getStyleByStyleName(String styleName);

    List<Style> getAllStyle();

    Style getStyleByID(Integer ID) throws CustomException;
    List<Style> getListStyleByUserID(String UserID) throws CustomException;
}
