package com.tttm.Whear.App.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "subRole")
public class SubRole {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer subRoleID;
  @Column(name = "subRoleName")
  private String subRoleName;
  @Column(name = "numberOfCollection")
  private Integer numberOfCollection;
  @Column(name = "numberOfClothes")
  private Integer numberOfClothes;
}
