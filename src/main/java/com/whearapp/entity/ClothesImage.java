package com.whearapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "clothes_image")
public class ClothesImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int imgID;
  private String imgUrl;
  @JoinColumn(name = "clothesID")
  @ManyToOne
  private Clothes clothesID;
}
