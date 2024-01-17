package com.tttm.Whear.App.constant;

import lombok.Getter;

@Getter
public enum ConstantMessage {
  CREATE_SUCCESS("Create Success"),
  CREATE_FAIL("Create Fail"),
  INVALID_USERNAME_OR_PASSWORD("Invalid Username or Password"),
  CANNOT_FIND_USER_BY_USERNAME("Can not find User by Username"),
  CANNOT_FIND_USER_BY_USERID("Can not find User by UserID"),
  USERID_IS_EMPTY_OR_NOT_EXIST("UserID is empty or not exist"),
  USERNAME_IS_EXIST("Username has existed already"),
  PHONE_IS_EXIST("Phone has existed already"),
  EMAIL_IS_EXIST("Email has existed already"),
  INVALID_ARGUMENT("Invalid argument"),
  MISSING_ARGUMENT("Some Arguments are missing or empty"),
  FORBIDDEN("Customer do not have role to access the page"),
  RESOURCE_NOT_FOUND("Resource not found"),
  REACH_MAXIMUM_COLLECTION("Please update plan to get more collection"),
  REQUIRED_UPDATE_PLAN("Update plan to use this feature");

  private final String message;

  ConstantMessage(String message) {
    this.message = message;
  }
}
