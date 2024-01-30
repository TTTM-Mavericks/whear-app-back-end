package com.tttm.Whear.App.service;

import com.tttm.Whear.App.entity.Clothes;
import com.tttm.Whear.App.exception.CustomException;
import com.tttm.Whear.App.utils.request.ClothesRequest;
import com.tttm.Whear.App.utils.response.ClothesResponse;
import java.util.List;

public interface ClothesService {

  public ClothesResponse createClothes(ClothesRequest clothesRequest) throws CustomException;

  public List<ClothesResponse> getAllClothes();

  public ClothesResponse getClothesByID(Integer clothesID) throws CustomException;

  public Clothes getClothesEntityByID(Integer clothesID) throws CustomException;

  public ClothesResponse updateClothes(ClothesRequest clothesRequest) throws CustomException;

  public void deleteClothesByID(Integer clothesID) throws CustomException;
}
