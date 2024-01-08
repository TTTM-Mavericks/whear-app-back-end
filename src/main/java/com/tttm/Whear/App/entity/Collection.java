package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

  @OneToMany(mappedBy = "collectionClothesKey.collections", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<CollectionClothes> collectionClothesList;
}
