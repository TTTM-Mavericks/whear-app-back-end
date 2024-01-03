package com.whearapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "collection")
public class Collection {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int collectionID;
  private String nameOfCollection;
  private int numberOfClothes;
  private String typeOfCollection;
  @JoinColumn(name = "userID")
  @ManyToOne
  private Users userID;
}
