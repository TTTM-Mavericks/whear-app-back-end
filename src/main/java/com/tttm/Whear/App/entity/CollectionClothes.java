package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "collection_clothes")
public class CollectionClothes {

  @EmbeddedId
  private CollectionClothesKey collectionClothesKey;

  @Embeddable
  public class CollectionClothesKey implements Serializable {

    @ManyToOne
    @JoinColumn(name = "collectionID", referencedColumnName = "collectionID", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private Collection collections;

    @ManyToOne
    @JoinColumn(name = "clothesID", referencedColumnName = "clothesID", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private Clothes clothes;
  }
}
