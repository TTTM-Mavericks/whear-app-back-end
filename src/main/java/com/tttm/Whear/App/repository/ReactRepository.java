package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.React;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactRepository extends JpaRepository<React, React.UserPostReactKey> {

}
