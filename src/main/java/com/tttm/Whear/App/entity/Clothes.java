package com.tttm.Whear.App.entity;

import com.tttm.Whear.App.enums.ClothesType;
import com.tttm.Whear.App.enums.MaterialType;
import com.tttm.Whear.App.enums.SeasonType;
import com.tttm.Whear.App.enums.ShapeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "clothes")
public class Clothes implements Serializable {

  @Id
  @Column(name = "clothesID")
  private Integer clothesID;

  @OneToOne
  @MapsId
  @JoinColumn(name = "clothesID", referencedColumnName = "postID", nullable = false, insertable = false, updatable = false)
  private Post posts;

  @Column(name = "nameOfProduct")
  private String nameOfProduct;

  @Column(name = "typeOfClothes")
  @Enumerated(EnumType.STRING)
  private ClothesType typeOfClothes;

  @Column(name = "shape")
  @Enumerated(EnumType.STRING)
  private ShapeType shape;

  @Column(name = "seasons")
  @Enumerated(EnumType.STRING)
  private SeasonType seasons;

  @Column(name = "description")
  private String description;

  @Column(name = "link")
  private String link;

  @Column(name = "rating")
  private Integer rating;

  @Column(name = "materials")
  @Enumerated(EnumType.STRING)
  private MaterialType materials;
}
