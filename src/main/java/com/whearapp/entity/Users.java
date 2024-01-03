package com.whearapp.entity;

import com.whearapp.enums.Language;
import com.whearapp.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class Users {
  @Id
  private String username;
  @Column
  private String password;
  @Column
  private LocalDate dateOfBirth;
  @Column
  private String phone;
  @Column
  private String email;
  @Column
  private boolean gender;
  @Column
  private UserRole role;
  @Column
  private String imgUrl;
  @Column
  private boolean status;
  @Column
  private Language language;
}
