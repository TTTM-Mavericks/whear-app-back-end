package com.tttm.Whear.App.service;

import com.tttm.Whear.App.entity.ClothesColor;
import com.tttm.Whear.App.exception.CustomException;
import java.util.List;

public interface ClothesColorService {

  public void createColor(Integer clothesID, String color) throws CustomException;

  public ClothesColor findByName(Integer clothesID, String color) throws CustomException;

  public List<ClothesColor> getAllColorOfClothes(Integer clothesID);
}
