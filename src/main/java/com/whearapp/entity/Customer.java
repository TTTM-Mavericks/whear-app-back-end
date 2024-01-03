package com.whearapp.entity;

import com.whearapp.enums.CustomerSubRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {
  @Id
  @OneToOne(targetEntity = Users.class)
  @JoinColumn(name = "customerID")
  private String customerID;
  private boolean isFirstLogin;
  private CustomerSubRole subRole;
  @OneToMany(mappedBy = "customerID", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<History> history;
}
