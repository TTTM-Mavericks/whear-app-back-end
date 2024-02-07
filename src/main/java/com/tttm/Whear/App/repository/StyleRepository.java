package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.Style;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StyleRepository extends JpaRepository<Style, Integer> {
    @Query(
            value = "select * from style where style_name = ?1", nativeQuery = true
    )
    Style getStyleByStyleName(String styleName);
}
