package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.constant.ConstantMessage;
import com.tttm.Whear.App.entity.Collection;
import com.tttm.Whear.App.entity.Customer;
import com.tttm.Whear.App.entity.SubRole;
import com.tttm.Whear.App.enums.ERole;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.repository.CollectionRepository;
import com.tttm.Whear.App.repository.SubRoleRepository;
import com.tttm.Whear.App.service.CollectionService;
import com.tttm.Whear.App.service.CustomerService;
import com.tttm.Whear.App.service.UserService;
import com.tttm.Whear.App.utils.request.CollectionRequest;
import com.tttm.Whear.App.utils.response.CollectionResponse;
import com.tttm.Whear.App.utils.response.UserResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CollectionServiceImpl implements CollectionService {

  private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
  @Autowired
  CollectionRepository collectionRepository;
  @Autowired
  UserService userService;
  private final CustomerService customerService;
  private final SubRoleRepository subRoleRepository;

  @Override
  public List<CollectionResponse> getCollectionsOfUser(String username) throws CustomException {
    try {
      if (username.isBlank() || username.isEmpty()) {
        logger.error(ConstantMessage.USERNAME_IS_EMPTY_OR_NOT_EXIST.getMessage());
        throw new CustomException(ConstantMessage.USERNAME_IS_EMPTY_OR_NOT_EXIST.getMessage());
      }
      UserResponse user = userService.getUserbyUsername(username);
      if (user == null) {
        logger.warn(ConstantMessage.CANNOT_FIND_USER_BY_USERNAME.getMessage());
        throw new CustomException(ConstantMessage.CANNOT_FIND_USER_BY_USERNAME.getMessage());
      }
      String userID = user.getUserID();
      List<Collection> collectionList = collectionRepository.getAllByUserID(userID);
      List<CollectionResponse> responseList = null;
      for (Collection col : collectionList) {
        if (responseList == null) {
          responseList = new ArrayList<>();
        }
        responseList.add(CollectionResponse.builder()
            .collectionID(col.getCollectionID())
            .nameOfCollection(col.getNameOfCollection())
            .typeOfCollection(col.getTypeOfCollection())
            .numberOfClothes(col.getNumberOfClothes())
            .collectionStatus(col.getCollectionStatus())
            .imgUrl(col.getImgUrl())
            .build()
        );
      }
      return responseList;
    } catch (Exception ex) {
      throw ex;
    }
  }

  @Override
  public CollectionResponse getCollectionByCollectionID(Integer collectionID) {
    CollectionResponse collectionResponse = null;
    try {
      Collection collection = collectionRepository.findByCollectionID(collectionID);
      if (collection != null) {
        collectionResponse = CollectionResponse.builder()
            .collectionID(collection.getCollectionID())
            .nameOfCollection(collection.getNameOfCollection())
            .typeOfCollection(collection.getTypeOfCollection())
            .numberOfClothes(collection.getNumberOfClothes())
            .imgUrl(collection.getImgUrl())
            .build();
      }
      return collectionResponse;
    } catch (Exception ex) {
      throw ex;
    }
  }

  @Override
  public CollectionResponse updateCollectionByID(CollectionRequest collection) {
    CollectionResponse collectionResponse = null;
    try {
      Collection newCollection = collectionRepository.findByCollectionID(
          collection.getCollectionID());
      if (newCollection != null) {
        // update collection entity
        newCollection.setNameOfCollection(collection.getNameOfCollection());
        newCollection.setTypeOfCollection(collection.getTypeOfCollection());
        newCollection.setNumberOfClothes(collection.getNumberOfClothes());

        // update to db
        collectionRepository.save(newCollection);

        // generate response
        collectionResponse = CollectionResponse.builder()
            .collectionID(newCollection.getCollectionID())
            .nameOfCollection(newCollection.getNameOfCollection())
            .typeOfCollection(newCollection.getTypeOfCollection())
            .numberOfClothes(newCollection.getNumberOfClothes())
            .collectionStatus(newCollection.getCollectionStatus())
            .imgUrl(newCollection.getImgUrl())
            .build();
      }
      return collectionResponse;
    } catch (Exception ex) {
      throw ex;
    }
  }

  @Override
  public void deleteCollectionByID(Integer collectionID) {
    try {
      Collection collection = collectionRepository.findByCollectionID(collectionID);
      if (collection != null) {
        collectionRepository.delete(collection);
      }
    } catch (Exception ex) {
      throw ex;
    }
  }

  @Override
  public CollectionResponse createCollection(CollectionRequest collection) throws CustomException {
    try {
      String username = collection.getUserID();
      if (username.isBlank() || username.isEmpty()) {
        logger.error(ConstantMessage.USERNAME_IS_EMPTY_OR_NOT_EXIST.getMessage());
        throw new CustomException(ConstantMessage.USERNAME_IS_EMPTY_OR_NOT_EXIST.getMessage());
      }
      UserResponse user = userService.getUserbyUsername(username);
      if (user == null) {
        logger.warn(ConstantMessage.CANNOT_FIND_USER_BY_USERNAME.getMessage());
        throw new CustomException(ConstantMessage.CANNOT_FIND_USER_BY_USERNAME.getMessage());
      }
      String userID = user.getUserID();
      if (user.getRole().equals(ERole.CUSTOMER)) {
        Customer customer = customerService.getCustomerByID(userID);
        SubRole subRole = subRoleRepository.getSubRolesBySubRoleID(customer.getSubRoleID());
        if (collectionRepository.getAllByUserID(userID).size() + 1
            > subRole.getNumberOfCollection()) {
          logger.error(ConstantMessage.REACH_MAXIMUM_COLLECTION.getMessage());
          throw new CustomException(ConstantMessage.REACH_MAXIMUM_COLLECTION.getMessage());
        }
      }
      CollectionResponse response = null;
      Collection col = Collection.builder()
          .collectionID(collection.getCollectionID())
          .typeOfCollection(collection.getTypeOfCollection())
          .nameOfCollection(collection.getNameOfCollection())
          .numberOfClothes(collection.getNumberOfClothes())
          .collectionStatus(collection.getCollectionStatus())
          .userID(userID)
          .imgUrl(collection.getImgUrl())
          .build();
      Collection newCollection = collectionRepository.save(col);

      response = CollectionResponse.builder()
          .collectionID(newCollection.getCollectionID())
          .nameOfCollection(newCollection.getNameOfCollection())
          .typeOfCollection(newCollection.getTypeOfCollection())
          .numberOfClothes(newCollection.getNumberOfClothes())
          .collectionStatus(newCollection.getCollectionStatus())
          .imgUrl(newCollection.getImgUrl())
          .build();
      return response;
    } catch (Exception ex) {
      throw ex;
    }
  }
}
