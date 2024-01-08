package com.tttm.Whear.App.utils.request;

import lombok.Data;

@Data
public class CollectionRequest {

  private Integer collectionID;
  private String userID;
  private String nameOfCollection;
  private String typeOfCollection;
  private Integer numberOfClothes;
}
