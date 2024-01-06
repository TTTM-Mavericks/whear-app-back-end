package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.ClothesSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClothesSizeRepository extends JpaRepository<ClothesSize, Integer> {
}
