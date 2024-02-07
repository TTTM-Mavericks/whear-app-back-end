package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.entity.Style;
import com.tttm.Whear.App.repository.StyleRepository;
import com.tttm.Whear.App.service.StyleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StyleServiceImpl implements StyleService {
    private final StyleRepository styleRepository;

    @Override
    public Style getStyleByStyleName(String styleName) {
        return styleRepository.getStyleByStyleName(styleName);
    }

    @Override
    public List<Style> getAllStyle() {
        return styleRepository.findAll();
    }
}
