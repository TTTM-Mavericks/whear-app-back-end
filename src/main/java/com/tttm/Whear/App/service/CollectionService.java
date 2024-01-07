package com.tttm.Whear.App.service;

import com.tttm.Whear.App.entity.Collection;
import com.tttm.Whear.App.utils.response.CollectionResponse;
import java.util.List;

public interface CollectionService {
  public List<CollectionResponse> getCollectionsOfUser(String userID);
  public CollectionResponse getCollectionByCollectionID(Integer collectionID);
//  public CollectionResponse updateCollectionByID(Integer collectionID, Collection collection);
}
