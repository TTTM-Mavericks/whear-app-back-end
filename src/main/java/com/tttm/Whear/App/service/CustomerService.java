package com.tttm.Whear.App.service;

import com.tttm.Whear.App.entity.Customer;
import com.tttm.Whear.App.entity.User;

public interface CustomerService {
  Customer createNewCustomers(User username);
  Customer getCustomerByID(String customerID);
}
