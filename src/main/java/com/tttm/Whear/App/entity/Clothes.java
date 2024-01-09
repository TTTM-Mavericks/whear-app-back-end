package com.tttm.Whear.App.entity;

import com.tttm.Whear.App.enums.SeasonType;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "clothes")
public class Clothes {

  @Id
  @Column(name = "clothesID", unique = true, nullable = false)
  private Integer clothesID;

  @OneToOne
  @MapsId
  @JoinColumn(name = "clothesID", referencedColumnName = "postID", nullable = false, insertable = false, updatable = false)
  private Post posts;

  @Column(name = "nameOfProduct", unique = false, nullable = false)
  private String nameOfProduct;

  @Column(name = "typeOfClothes", unique = false, nullable = false)
  private String typeOfClothes;

  @Column(name = "shape", unique = false, nullable = false)
  private String shape;

  @Column(name = "seasons", unique = false, nullable = false)
  @Enumerated(EnumType.STRING)
  private SeasonType seasons;

  @Column(name = "description", unique = false, nullable = false)
  private String description;

  @Column(name = "link", unique = false, nullable = false)
  private String link;

  @Column(name = "rating", unique = false, nullable = false)
  private Integer rating;

  @Column(name = "materials", unique = false, nullable = false)
  private String materials;
}
