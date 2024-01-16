package com.tttm.Whear.App.utils.request;

import com.tttm.Whear.App.enums.SeasonType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClothesRequest {
  private Integer clothesID;
  private String nameOfProduct;
  private String typeOfClothes;
  private String shape;
  private SeasonType seasons;
  private String description;
  private String link;
  private Integer rating;
  private String materials;
}
