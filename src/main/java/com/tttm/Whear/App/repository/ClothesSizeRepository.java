package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.ClothesSize;
import com.tttm.Whear.App.entity.ClothesSizeKey;
import com.tttm.Whear.App.enums.SizeType;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesSizeRepository extends JpaRepository<ClothesSize, ClothesSizeKey> {

  @Modifying
  @Transactional
  @Query(value = "insert into clothes_size (clothesid, size) values (?1, ?2)", nativeQuery = true)
  void insertClothesSize(Integer clothesid, SizeType size);

  @Modifying
  @Transactional
  @Query(value = "delete from clothes_size where clothes_size.clothesid = ?1 and clothes_size.size = ?2", nativeQuery = true)
  void deleteClothesSize(Integer clothesid, String size);

  @Transactional
  @Query(value = "select * from clothes_size where clothes_size.clothesid = ?1 and clothes_size.size = ?2", nativeQuery = true)
  ClothesSize findByName(Integer clothesid, String size);

  @Transactional
  @Query(value = "select * from clothes_size where clothes_size.clothesid = ?1", nativeQuery = true)
  List<ClothesSize> getAllSizeOfClothes(Integer clothesid);
}
