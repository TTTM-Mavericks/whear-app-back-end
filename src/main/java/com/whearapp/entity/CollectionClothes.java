package com.whearapp.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "collection_clothes")
public class CollectionClothes {
  @EmbeddedId
  private CollectionClothesKey collectionClothesKey;
}
@Embeddable
class CollectionClothesKey implements Serializable {
  @JoinColumn(name = "collectionID")
  @ManyToOne
  private Collection collectionID;
  @JoinColumn(name = "clothesID")
  @ManyToOne
  private Clothes clothesID;
}