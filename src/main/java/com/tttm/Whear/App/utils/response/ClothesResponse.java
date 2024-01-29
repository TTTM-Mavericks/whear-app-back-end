package com.tttm.Whear.App.utils.response;

import com.tttm.Whear.App.entity.ClothesColor;
import com.tttm.Whear.App.entity.ClothesImage;
import com.tttm.Whear.App.entity.ClothesSize;
import com.tttm.Whear.App.enums.SeasonType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClothesResponse {

  private Integer clothesID;
  private String nameOfProduct;
  private String typeOfClothes;
  private String shape;
  private String description;
  private String link;
  private Integer rating;
  private String materials;
  private List<String> clothesSeasons;
  private List<String> clothesImages;
  private List<String> clothesSizes;
  private List<String> clothesColors;
}
