package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "brand")
public class Brand {

  @Id
  @Column(name = "brandID", unique = true, nullable = false)
  private String brandID;

  @OneToOne
  @MapsId
  @JoinColumn(name = "brandID", referencedColumnName = "customerID", nullable = false, insertable = false, updatable = false)
  private Customer customer;

  @Column(name = "description", unique = false, nullable = false)
  private String description;

  @Column(name = "address", unique = false, nullable = false)
  private String address;

  @Column(name = "link", unique = false, nullable = false)
  private String link;
}
