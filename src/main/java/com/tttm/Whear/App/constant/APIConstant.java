package com.tttm.Whear.App.constant;

public class APIConstant {

  /**
   * Default API
   */
  public static final String API = "/api/v1";

  public class CollectionAPI{
    /**
     * Collection API
     */
    public static final String COLLECTION = APIConstant.API+"/collection";
    public static final String GET_ALL_COLLECTION_BY_USER_ID = "/get-all-by-userid";
    public static final String GET_COLLECTION_BY_ID = "/get-collection-by-id";
    public static final String UPDATE_COLLECTION_BY_ID = "update-collection-by-id";
  }

  public class UserAPI{
    /**
     * User API
     */
    public static final String USER = APIConstant.API + "/user";
    public static final String GET_ALL_USER = "/get-all-user";
    public static final String GET_USER_BY_USERNAME = "/get-user-by-username";
    public static final String GET_USER_BY_USERNAME_AND_PASSWORD = "/get-user-by-username-and-password";
    public static final String UPDATE_USER_BY_USERNAME = "/update-user-by-username";
    public static final String CREATE_NEW_USER = "/create-new-user";
    public static final String UPDATE_STATUS_USER = "/update-status-user";
  }
}
