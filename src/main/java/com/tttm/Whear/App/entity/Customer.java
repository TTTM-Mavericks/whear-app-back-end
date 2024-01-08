package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tttm.Whear.App.enums.SubRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "customer")
public class Customer {

  @Id
  @Column(name = "customerID", unique = true, nullable = false)
  private String customerID;

  @OneToOne
  @JoinColumn(name = "customerID", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
  private User user;

  @Column(name = "isFirstLogin", unique = false, nullable = false)
  private Boolean isFirstLogin;

  @Column(name = "subRole", unique = false, nullable = false)
  private SubRole subRole;

  @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<History> historyList;

  @OneToMany(mappedBy = "customerPayment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Payment> paymentList;

}
