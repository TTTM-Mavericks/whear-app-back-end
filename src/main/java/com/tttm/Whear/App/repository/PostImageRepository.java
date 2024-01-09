package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.PostImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostImageRepository extends JpaRepository<PostImages, Integer> {

}
