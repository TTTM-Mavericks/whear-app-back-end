package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.entity.Customer;
import com.tttm.Whear.App.entity.User;
import com.tttm.Whear.App.enums.ESubRole;
import com.tttm.Whear.App.repository.CustomerRepository;
import com.tttm.Whear.App.repository.SubRoleRepository;
import com.tttm.Whear.App.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
  private final CustomerRepository customerRepository;
  private final SubRoleRepository subRoleRepository;
  @Override
  @Caching(
           evict = @CacheEvict(cacheNames = "customers", allEntries = true),
          cacheable = @Cacheable(cacheNames = "customer", key = "#user.userID", condition = "#user.userID != null", unless = "#result == null")
  )
  public Customer createNewCustomers(User user) {
    return customerRepository.save(Customer
        .builder()
        .customerID(user.getUserID())
        .isFirstLogin(true)
        .subRoleID(subRoleRepository.getSubRolesBySubRoleName(ESubRole.LV1).getSubRoleID())
        .build());
  }

  @Cacheable(cacheNames = "customer", key = "#customerID", condition = "#customerID != null", unless = "#result == null")
  @Override
  public Customer getCustomerByID(String customerID) {
    return customerRepository.getReferenceById(customerID);
  }
}
