package com.tttm.Whear.App.constant;

public class APIConstant {

  /**
   * Default API
   */
  public static final String API = "/api/v1";

  public class CollectionAPI {

    /**
     * Collection API
     */
    public static final String COLLECTION = APIConstant.API + "/collection";
    public static final String GET_ALL_COLLECTION_BY_USER_ID = "/get-all-by-userid";
    public static final String GET_COLLECTION_BY_ID = "/get-collection-by-id";
    public static final String UPDATE_COLLECTION_BY_ID = "/update-collection-by-id";
    public static final String DELETE_COLLECTION_BY_ID = "/delete-collection-by-id";
    public static final String CREATE_COLLECTION = "/create-collection";
  }

  public class UserAPI {

    /**
     * User API
     */
    public static final String USER = APIConstant.API + "/user";
    public static final String GET_ALL_USER = "/get-all-user";
    public static final String GET_USER_BY_USERNAME = "/get-user-by-username";
    public static final String GET_USER_BY_USERID = "/get-user-by-userid";
    public static final String GET_USER_BY_EMAIL_AND_PASSWORD = "/get-user-by-email-and-password";
    public static final String UPDATE_USER_BY_USERID = "/update-user-by-userid";
    public static final String CREATE_NEW_USER = "/create-new-user";
    public static final String UPDATE_STATUS_USER = "/update-status-user";
  }

  public class PostAPI {

    /**
     * Post API
     */
    public static final String POST = APIConstant.API + "/post";
    public static final String CREATE_POST = "/create-post";
    public static final String GET_POST_BY_POST_ID = "/get-post-by-postid";
    public static final String GET_ALL_POST = "/get-all-post";
    public static final String GET_POST_BY_TYPE_OF_POST = "/get-post-by-type-of-post";
    public static final String GET_POST_BY_HASHTAG = "/get-post-by-hashtag";
    public static final String GET_POST_IN_RANGE = "/get-post-in-range";
    public static final String DELETE_POST_BY_POSTID = "/delete-by-postid";
    public static final String UPDATE_POST = "/update-post";
  }

  public class FollowAPI {

    /**
     * Follow API
     */
    public static final String FOLLOW = APIConstant.API + "/follow";
    public static final String USER_FOLLOW_OR_UNFOLLOW_ANOTHER_USER = "/un-follow";
    public static final String GET_ALL_FOLLOWER_USER = "/get-all-follower-user";
    public static final String GET_ALL_FOLLOWING_USER = "/get-all-following-user";
  }

  public class CustomerAPI {

    /**
     * Customer API
     */
    public static final String FOLLOW = APIConstant.API + "/customer";
  }

  public class ClothesAPI {

    /**
     * Clothes API
     */
    public static final String CLOTHES = APIConstant.API + "/clothes";
    public static final String CREATE_CLOTHES = "/create-clothes";
    public static final String GET_ALL_CLOTHES = "/get-all-clothes";
    public static final String GET_CLOTHES_BY_ID = "/get-clothes-by-id";
  }

  public class NotificationAPI {

    /**
     * Notification API
     */
    public static final String NOTIFICATION = APIConstant.API + "/notification";
    public static final String GET_ALL_NOTIFICATION = "/get-all-notification";
    public static final String GET_UNREAD_NOTIFICATION = "/get-unread-notification";
    public static final String UN_READ_NOTIFICATION = "/un-read-notification";
  }
}
