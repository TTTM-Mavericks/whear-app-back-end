package com.tttm.Whear.App.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class ClothesColorKey implements Serializable {
  @ManyToOne(targetEntity = Clothes.class)
  private Integer clothesID;
  private String color;
}
