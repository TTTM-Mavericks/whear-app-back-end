package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  User getUserByEmail(String email);

  User getUserByUsername(String username);

  User getUserByPhone(String phone);

  User getUserByUsernameAndPassword(String username, String password);
}
