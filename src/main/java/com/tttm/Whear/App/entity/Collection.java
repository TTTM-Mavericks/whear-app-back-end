package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "collection")
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collectionID", unique = true, nullable = false)
    private Integer collectionID;

    @Column(name = "nameOfCollection", unique = false, nullable = false)
    private String nameOfCollection;

    @Column(name = "numberOfClothes", unique = false, nullable = false)
    private Integer numberOfClothes;

    @Column(name = "typeOfCollection", unique = false, nullable = false)
    private String typeOfCollection;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private User userCollection;

    @OneToMany(mappedBy = "collections", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<CollectionClothes> collectionClothesList;
}
