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
  @Query(value = "insert into clothes (clothesid, description, link, materials, name_of_product, rating, shape, type_of_clothes, create_date, last_modified_date) values (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, current_timestamp, current_timestamp)", nativeQuery = true)
  void insertClothes(Integer clothesid, String desctiption, String link, String materials,
      String name, Integer rating, String shape, String type);

  public Clothes getClothesByClothesID(Integer clothesID);
}
