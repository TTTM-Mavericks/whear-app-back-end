package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.NewsImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsImageRepository extends JpaRepository<NewsImages, Integer> {

}
