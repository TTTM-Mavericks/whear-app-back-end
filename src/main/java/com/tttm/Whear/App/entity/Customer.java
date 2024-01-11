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
public class Customer {

  @Id
  @Column(name = "customerID", unique = true, nullable = false)
  private String customerID;

  @OneToOne
  @PrimaryKeyJoinColumn(name = "customerID", referencedColumnName = "userID")
  private User user;

  @Column(name = "isFirstLogin", unique = false, nullable = false)
  private Boolean isFirstLogin;

  @Column(name = "subRoleID", unique = false, nullable = false)
  @PrimaryKeyJoinColumn()
  private Integer subRoleID;

}
