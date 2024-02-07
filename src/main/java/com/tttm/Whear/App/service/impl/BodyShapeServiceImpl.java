package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.entity.BodyShape;
import com.tttm.Whear.App.repository.BodyShapeRepository;
import com.tttm.Whear.App.service.BodyShapeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BodyShapeServiceImpl implements BodyShapeService {
    private final BodyShapeRepository bodyShapeRepository;

    @Override
    public BodyShape getBodyShapeByBodyShapeName(String bodyShapeName) {
        return bodyShapeRepository.getBodyShapeByBodyShapeName(bodyShapeName);
    }

    @Override
    public List<BodyShape> getAllBodyShape() {
        return bodyShapeRepository.findAll();
    }
}
