package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.constant.ConstantMessage;
import com.tttm.Whear.App.entity.Style;
import com.tttm.Whear.App.entity.User;
import com.tttm.Whear.App.entity.UserStyle;
import com.tttm.Whear.App.entity.UserStyleKey;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.repository.UserStyleRepository;
import com.tttm.Whear.App.service.HistoryService;
import com.tttm.Whear.App.service.StyleService;
import com.tttm.Whear.App.service.UserService;
import com.tttm.Whear.App.service.UserStyleService;
import com.tttm.Whear.App.utils.request.StyleAndBodyShapeRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserStyleServiceImpl implements UserStyleService {
    private final UserStyleRepository userStyleRepository;
    private final UserService userService;
    private final HistoryService historyService;
    private final StyleService styleService;
    @Override
    @Transactional
    public void createStyleAndBodyShape(StyleAndBodyShapeRequest request) throws CustomException {
        Optional.of(request.getUserID())
                .filter(id -> !id.isEmpty() && !id.isBlank())
                .orElseThrow(() -> new CustomException(ConstantMessage.USERID_IS_EMPTY_OR_NOT_EXIST.getMessage()));

        User user = userService.getUserEntityByUserID(request.getUserID());
        if (user == null) {
            throw new CustomException(ConstantMessage.CANNOT_FIND_USER_BY_USERID.getMessage() + " : " + request.getUserID());
        }

        if(request.getBodyShapeName().isBlank() || request.getBodyShapeName().isEmpty())
        {
            throw new CustomException("Body Shape can not be null");
        }

        if(request.getListStyles().size() == 0)
        {
            throw new CustomException("Style can not be null");
        }

        userService.updateBodyShapeToUser(user, request.getBodyShapeName());

        request.getListStyles().forEach(styleName -> {
            Style style = styleService.getStyleByStyleName(styleName);
            if (style != null) {
                if(userStyleRepository.findUserStyleByStyleIDAndUserID(style.getStyleID(), request.getUserID()) == null)
                {
                    userStyleRepository.createUserStyle(style.getStyleID(), request.getUserID());
                }
                else try {
                    throw new CustomException(ConstantMessage.STYLE_ID_AND_USER_ID_IS_EXIST.getMessage());
                } catch (CustomException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    throw new CustomException(ConstantMessage.CAN_NOT_FIND_STYLE_NAME.getMessage() + " : " + styleName);
                } catch (CustomException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
