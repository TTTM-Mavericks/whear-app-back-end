package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tttm.Whear.App.Enums.SeasonType;
import com.tttm.Whear.App.Enums.SizeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "clothes")
public class Clothes {
    @Id
    @Column(name = "clothesID", unique = true, nullable = false)
    private Integer clothesID;

    @OneToOne
    @JoinColumn(name = "clothesID", referencedColumnName = "postID", nullable = false, insertable = false, updatable = false)
    private User user;

    @Column(name = "nameOfProduct", unique = false, nullable = false)
    private String nameOfProduct;

    @Column(name = "typeOfClothes", unique = false, nullable = false)
    private String typeOfClothes;

    @Column(name = "colors", unique = false, nullable = false)
    private String colors;

    @Column(name = "shape", unique = false, nullable = false)
    private String shape;

    @Column(name = "seasons", unique = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private SeasonType seasons;

    @Column(name = "size", unique = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private SizeType size;

    @Column(name = "description", unique = false, nullable = false)
    private String description;

    @Column(name = "link", unique = false, nullable = false)
    private String link;

    @Column(name = "rating", unique = false, nullable = false)
    private Integer rating;

    @Column(name = "materials", unique = false, nullable = false)
    private String materials;

    @OneToMany(mappedBy = "images", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ClothesImage> clothesImagesList;

    @OneToMany(mappedBy = "clothes", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CollectionClothes> clothesCollectionList;
}
