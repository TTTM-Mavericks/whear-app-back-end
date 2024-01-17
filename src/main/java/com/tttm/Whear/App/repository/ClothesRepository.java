package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.Clothes;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesRepository extends JpaRepository<Clothes, Integer> {

  @Modifying
  @Transactional
  @Query(value = "insert into clothes (clothesid, description, link, materials, name_of_product, rating, seasons, shape, type_of_clothes) values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9)", nativeQuery = true)
  void insertClothes(Integer clothesid, String desctiption, String link, String materials,
      String name, Integer rating, String seasons, String shape, String type);

  public Clothes getClothesByClothesID(Integer clothesID);
}
