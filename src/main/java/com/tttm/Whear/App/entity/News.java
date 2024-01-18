package com.tttm.Whear.App.entity;

import com.tttm.Whear.App.enums.StatusGeneral;
import com.tttm.Whear.App.enums.TypeOfNews;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "news")
public class News implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "newsID")
  private Integer newsID;

  @Column(name = "brandID")
  private String brandID;
  @ManyToOne
  @JoinColumn(name = "brandID", referencedColumnName = "brandID", nullable = false, insertable = false, updatable = false)
  private Brand brand;

  @Column(name = "title")
  private String title;

  @Column(name = "content")
  private String content;

  @Column(name = "typeOfNews")
  @Enumerated(EnumType.STRING)
  private TypeOfNews typeOfNews;

  @Column(name = "status")
  private StatusGeneral status;
}
