package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tttm.Whear.App.enums.StatusGeneral;
import com.tttm.Whear.App.enums.TypeOfNews;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "news")
public class News {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "newsID", unique = true, nullable = false)
  private Integer newsID;

  @ManyToOne
  @JoinColumn(name = "brandID", referencedColumnName = "brandID", nullable = false, insertable = false, updatable = false)
  @JsonBackReference
  private Brand brand;

  @Column(name = "title", unique = false, nullable = false)
  private String title;

  @Column(name = "content", unique = false, nullable = false)
  private String content;

  @Column(name = "typeOfNews", unique = false, nullable = false)
  @Enumerated(EnumType.STRING)
  private TypeOfNews typeOfNews;

  @Column(name = "status", unique = false, nullable = false)
  private StatusGeneral status;

  @OneToMany(mappedBy = "images", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<NewsImages> newsImageList;
}
