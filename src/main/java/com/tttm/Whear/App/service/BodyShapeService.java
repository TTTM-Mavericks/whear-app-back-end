package com.tttm.Whear.App.service;


import com.tttm.Whear.App.entity.BodyShape;
import com.tttm.Whear.App.exception.CustomException;

import java.util.List;

public interface BodyShapeService {
    BodyShape getBodyShapeByBodyShapeName(String bodyShapeName);

    List<BodyShape> getAllBodyShape();

    BodyShape getBodyShapeByID(Integer ID) throws CustomException;
}
