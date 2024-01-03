package com.whearapp.entity;

import com.whearapp.enums.TypeOfNews;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "news")
public class News {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int newsID;
  @JoinColumn(name = "brandID")
  @ManyToOne
  private Brand brandID;
  private String title;
  private String content;
  private TypeOfNews typeOfNews;
  private boolean status;
}
