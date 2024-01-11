package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tttm.Whear.App.enums.StatusCollection;
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

  @Column(name = "typeOfCollection")
  private String typeOfCollection;

  @Column(name = "collectionStatus")
  private StatusCollection collectionStatus;

  @Column(name = "userID", nullable = false)
  private String userID;
  @ManyToOne
  @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false, insertable = false, updatable = false)
  private User user;

  @Column(name = "imgUrl")
  private String imgUrl;
}
