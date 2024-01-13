package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.CollectionClothes;
import com.tttm.Whear.App.entity.CollectionClothesKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionClothesRepository extends
    JpaRepository<CollectionClothes, CollectionClothesKey> {

}
