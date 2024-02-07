package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.Style;
import com.tttm.Whear.App.entity.UserStyle;
import com.tttm.Whear.App.entity.UserStyleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStyleRepository extends JpaRepository<UserStyle, UserStyleKey> {
}
