package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "clothes_image")
public class ClothesImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "imgID", unique = true, nullable = false)
  private Integer imgID;

  @Column(name = "imageUrl", unique = false, nullable = false)
  private String imageUrl;

  @ManyToOne(targetEntity = Clothes.class)
  @JoinColumn(name = "clothesID")
  private Integer clothesID;
//  @ManyToOne
//  @JoinColumn(name = "clothesID", referencedColumnName = "clothesID", nullable = false)
//  @JsonBackReference
//  private Clothes images;
}
