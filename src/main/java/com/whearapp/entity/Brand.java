package com.whearapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "brand")
public class Brand {
  @Id
  @JoinColumn(name = "brandID")
  @OneToOne(targetEntity = Users.class)
  private String brandID;

  private String description;
  private String address;
  private String link;
}
