package com.tttm.Whear.App.entity;

import com.tttm.Whear.App.enums.ColorType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
@Table(name = "clothes_size")
public class ClothesSize {
  @EmbeddedId
  ClothesSizeKey clothesSizeKey;
//  @Id
//  @Column(name = "clothesID", unique = true, nullable = false)
//  private Integer clothesID;
//
//  @ManyToOne
//  @MapsId
//  @JoinColumn(name = "clothesID", referencedColumnName = "clothesID", nullable = false, insertable = false, updatable = false)
//  private Clothes clothesSize;
//
//  @Column(name = "colorType", unique = false, nullable = true)
//  private ColorType colorType;
}
