package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.constant.ConstantMessage;
import com.tttm.Whear.App.entity.History;
import com.tttm.Whear.App.entity.User;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.repository.HistoryRepository;
import com.tttm.Whear.App.service.HistoryService;
import com.tttm.Whear.App.service.UserService;
import com.tttm.Whear.App.utils.request.HistoryRequest;
import com.tttm.Whear.App.utils.response.HistoryResponse;
import com.tttm.Whear.App.utils.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {
    private final HistoryRepository historyRepository;

    private final UserService userService;
    @Override
    public void createHistoryItem(HistoryRequest historyRequest)  throws CustomException {
        Optional.of(historyRequest.getCustomerID())
                .filter(id -> !id.isEmpty() && !id.isBlank())
                .orElseThrow(() -> new CustomException(ConstantMessage.USERID_IS_EMPTY_OR_NOT_EXIST.getMessage()));

        User user = userService.getUserEntityByUserID(historyRequest.getCustomerID());
        if(user == null)
        {
            throw new CustomException(ConstantMessage.CANNOT_FIND_USER_BY_USERID.getMessage());
        }

        historyRequest
                .getHistoryItems()
                .forEach(historyItem ->  historyRepository
                        .createHistoryItem(historyRequest.getCustomerID(),
                        historyItem.toUpperCase())
                );
    }

    @Override
    public HistoryResponse getAllHistoryItemsByCustomerID(String customerID) throws CustomException {
        List<String> historyItems = historyRepository
                .getAllHistoryItemsByCustomerID(customerID)
                .stream()
                .map(history -> history.getHistoryItem())
                .toList();

        return convertToHistoryResponse(customerID, historyItems);
    }

    public HistoryResponse convertToHistoryResponse(String customerID,  List<String> historyItem)
    {
        return HistoryResponse
                .builder()
                .customerID(customerID)
                .historyItems(historyItem)
                .build();

    }
}
