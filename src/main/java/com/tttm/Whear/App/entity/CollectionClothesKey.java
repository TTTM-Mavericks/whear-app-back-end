package com.tttm.Whear.App.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Embeddable
public class CollectionClothesKey implements Serializable {
    @Column(name = "clothesID", unique = true, nullable = false)
    private Integer clothesID;
    @ManyToOne
    @JoinColumn(name = "clothesID", referencedColumnName = "clothesID", nullable = false, insertable = false, updatable = false)
    private Clothes clothes;
    @Column(name = "collectionID", unique = true, nullable = false)
    private Integer collectionID;
    @ManyToOne
    @JoinColumn(name = "collectionID", referencedColumnName = "collectionID", nullable = false, insertable = false, updatable = false)
    private Collection collection;
}