package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

  @Id
  @Column(name = "customerID")
  private String customerID;

  @OneToOne
  @PrimaryKeyJoinColumn(name = "customerID", referencedColumnName = "userID")
  private User user;

  @Column(name = "isFirstLogin")
  private Boolean isFirstLogin;

  @Column(name = "subRoleID")
  @PrimaryKeyJoinColumn()
  private Integer subRoleID;

}
