package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "history")
public class History implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "historyID", unique = true, nullable = false)
  private Integer historyID;

  @Column(name = "customerID", unique = true, nullable = false)
  private String customerID;
  @ManyToOne
  @JoinColumn(name = "customerID", referencedColumnName = "customerID", nullable = false, insertable = false, updatable = false)
  private Customer customer;

  @Column(name = "historyItem", unique = false, nullable = true)
  private String historyItem;
}
