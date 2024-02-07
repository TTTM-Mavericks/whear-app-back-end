package com.tttm.Whear.App.service;

import com.tttm.Whear.App.entity.Style;

import java.util.List;

public interface StyleService {
    Style getStyleByStyleName(String styleName);

    List<Style> getAllStyle();
}
