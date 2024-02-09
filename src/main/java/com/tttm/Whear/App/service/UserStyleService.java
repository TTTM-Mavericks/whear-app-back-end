package com.tttm.Whear.App.service;

import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.utils.request.StyleAndBodyShapeRequest;
import com.tttm.Whear.App.utils.request.UpdateStyleRequest;

public interface UserStyleService {
    void createStyleAndBodyShape(StyleAndBodyShapeRequest request) throws CustomException;

    void updateStyleForCustomer(UpdateStyleRequest updateStyleRequest) throws CustomException;
}
