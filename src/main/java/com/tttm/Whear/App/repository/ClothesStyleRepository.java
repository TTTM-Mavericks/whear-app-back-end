package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.BodyShape;
import com.tttm.Whear.App.entity.ClothesStyle;
import com.tttm.Whear.App.entity.ClothesStyleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesStyleRepository extends JpaRepository<ClothesStyle, ClothesStyleKey> {
}
