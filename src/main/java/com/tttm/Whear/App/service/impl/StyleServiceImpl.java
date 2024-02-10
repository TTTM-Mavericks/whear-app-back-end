package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.constant.ConstantMessage;
import com.tttm.Whear.App.entity.Style;
import com.tttm.Whear.App.exception.CustomException;
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

    @Override
    public Style getStyleByID(Integer ID) throws CustomException {
        return styleRepository.findById(ID)
                .orElseThrow(() -> new CustomException(ConstantMessage.ID_IS_EMPTY_OR_NOT_EXIST.getMessage()));
    }
}
