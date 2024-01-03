package com.whearapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "clothes")
public class Clothes {
  @Id
  @JoinColumn(name = "clothesID")
  @OneToOne(targetEntity = Post.class)
  private int clothesID;
  private String nameOfProduct;
  private String typeOfClothes;
  private String shape;
  private String seasons;
  private String description;
  private String link;
  private int rating;
  private String materials;
}
