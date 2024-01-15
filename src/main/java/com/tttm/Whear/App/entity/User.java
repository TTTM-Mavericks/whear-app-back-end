package com.tttm.Whear.App.entity;

import com.tttm.Whear.App.enums.ERole;
import com.tttm.Whear.App.enums.Language;
import com.tttm.Whear.App.enums.StatusGeneral;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements Serializable {
  @Id
  @Column(name = "userID", unique = true, nullable = false)
  private String userID;

  @Column(name = "username", unique = true, nullable = false)
  private String username;

  @Column(name = "password", unique = false, nullable = false)
  private String password;

  @Column(name = "dateOfBirth", unique = false, nullable = true)
  @Temporal(TemporalType.DATE)
  private Date dateOfBirth;

  @Column(name = "phone", unique = true, nullable = false)
  private String phone;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "gender", unique = false, nullable = true)
  private Boolean gender;

  @Column(name = "role", unique = false, nullable = true)
  @Enumerated(EnumType.STRING)
  private ERole role;

  @Column(name = "imgUrl", unique = false, nullable = true)
  private String imgUrl;

  @Column(name = "status", unique = false, nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusGeneral status;

  @Column(name = "language", unique = false, nullable = false)
  @Enumerated(EnumType.STRING)
  private Language language;
}
