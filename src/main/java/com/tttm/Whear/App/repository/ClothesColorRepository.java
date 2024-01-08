package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.ClothesColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesColorRepository extends JpaRepository<ClothesColor, Integer> {

}
