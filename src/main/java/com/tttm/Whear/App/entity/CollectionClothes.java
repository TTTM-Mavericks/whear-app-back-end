package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
