package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  User getUserByEmail(String email);

  User getUserByUserID(String userID);

  User getUserByPhone(String phone);

  User getUserByEmailAndPassword(String email, String password);
}
