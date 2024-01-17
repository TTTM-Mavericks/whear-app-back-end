package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.ClothesImage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesImageRepository extends JpaRepository<ClothesImage, Integer> {
  public List<ClothesImage> getAllByClothesID(Integer clothesID);
}
