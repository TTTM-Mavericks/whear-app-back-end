package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClothesSeasonRepository extends JpaRepository<ClothesSeason, ClothesSeasonKey> {
    @Modifying
    @Transactional
    @Query(value = "insert into clothes_season (clothesid, season, create_date, last_modified_date) values (?1, ?2, current_timestamp, current_timestamp)", nativeQuery = true)
    void insertClothesSeason(Integer clothesid, String season);

    @Modifying
    @Transactional
    @Query(value = "delete from clothes_season where clothes_season.clothesid = ?1 and clothes_season.season = ?2", nativeQuery = true)
    void deleteClothesSeason(Integer clothesid, String season);

    @Transactional
    @Query(value = "select * from clothes_season where clothes_season.clothesid = ?1 and clothes_season.season = ?2", nativeQuery = true)
    ClothesSeason findByName(Integer clothesid, String season);

    @Transactional
    @Query(value = "select * from clothes_season where clothes_season.clothesid = ?1", nativeQuery = true)
    List<ClothesSeason> getAllSeasonOfClothes(Integer clothesid);
}
