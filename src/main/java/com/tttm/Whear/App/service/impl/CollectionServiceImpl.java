package com.tttm.Whear.App.service.impl;

import com.tttm.Whear.App.entity.Collection;
import com.tttm.Whear.App.repository.CollectionRepository;
import com.tttm.Whear.App.service.CollectionService;
import com.tttm.Whear.App.utils.response.CollectionResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionServiceImpl implements CollectionService {

  @Autowired
  CollectionRepository collectionRepository;

  @Override
  public List<CollectionResponse> getCollectionsOfUser(String userID) {
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
          .build()
      );
    }
    return responseList;
  }
}
