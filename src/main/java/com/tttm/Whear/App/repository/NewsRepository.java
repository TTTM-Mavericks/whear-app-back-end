package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.News;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    @Modifying
    @Transactional
    @Query(value = "insert into news (brand_id, type_of_news, title, content, status, create_date, last_modified_date) values (?1, ?2, ?3, ?4, ?5, current_timestamp, null)", nativeQuery = true)
    void saveNews(Integer brandID, String typeOfNews, String title, String content, String status);

    @Modifying
    @Transactional
    @Query(value = "delete from news where newsid = ?1", nativeQuery = true)
    void deleteByNewsID(Integer postID);

    News getNewsByNewsIDIs(Integer newID);

    @Query(value = "select * from news where brandid = ?1 order by create_date desc", nativeQuery = true)
    List<News> getNewsByBrandID(Integer brandID);

    @Query(value = "select * from news where type_of_news = ?1 order by create_date desc", nativeQuery = true)
    List<News> getNewsByTypeOfNews(String typeOfNews);
}
