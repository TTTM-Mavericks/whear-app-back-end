package com.tttm.Whear.App.service;


import com.tttm.Whear.App.entity.BodyShape;

import java.util.List;

public interface BodyShapeService {
    BodyShape getBodyShapeByBodyShapeName(String bodyShapeName);

    List<BodyShape> getAllBodyShape();
}
