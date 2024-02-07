package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.Style;
import com.tttm.Whear.App.entity.UserStyle;
import com.tttm.Whear.App.entity.UserStyleKey;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStyleRepository extends JpaRepository<UserStyle, UserStyleKey> {
    @Modifying
    @Transactional
    @Query(value = "insert into user_style (styleid, userid, create_date, last_modified_date) values (?1, ?2, current_timestamp, null)", nativeQuery = true)
    void createUserStyle(Integer styleID, String userID);

    @Query(value = "select * from user_style where styleid = ?1 and userid = ?2", nativeQuery = true)
    UserStyle findUserStyleByStyleIDAndUserID(Integer styleID, String userID);
}
