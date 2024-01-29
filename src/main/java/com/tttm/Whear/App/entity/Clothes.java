package com.tttm.Whear.App.entity;

import com.tttm.Whear.App.entity.common.AuditEntity;
import com.tttm.Whear.App.enums.ClothesType;
import com.tttm.Whear.App.enums.MaterialType;
import com.tttm.Whear.App.enums.SeasonType;
import com.tttm.Whear.App.enums.ShapeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "clothes")
@EntityListeners(AuditingEntityListener.class)
public class Clothes extends AuditEntity implements Serializable {
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
