package com.tttm.Whear.App.service;

import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.utils.request.StyleAndBodyShapeRequest;

public interface UserStyleService {
    void createStyleAndBodyShape(StyleAndBodyShapeRequest request) throws CustomException;
}
