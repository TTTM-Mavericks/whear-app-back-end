package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.entity.Customer;
import com.tttm.Whear.App.entity.User;
import com.tttm.Whear.App.enums.SubRole;
import com.tttm.Whear.App.repository.CustomerRepository;
import com.tttm.Whear.App.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;
    @Override
    public Customer createNewCustomers(User user) {
        return customerRepository.save(Customer
                .builder()
                .customerID(user.getUsername())
                .isFirstLogin(true)
                .subRole(SubRole.LV1)
                .build());
    }
}
