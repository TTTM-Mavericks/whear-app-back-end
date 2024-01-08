package com.tttm.Whear.App.utils.response;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CollectionResponse implements Serializable {

  private Integer collectionID;
  private String nameOfCollection;
  private Integer numberOfClothes;
  private String typeOfCollection;
}
