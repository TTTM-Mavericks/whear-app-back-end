package com.whearapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "news_image")
public class NewsImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int imgID;
  private String imgUrl;
  @JoinColumn(name = "newsID")
  @ManyToOne
  private News newsID;
}
