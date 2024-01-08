package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tttm.Whear.App.enums.StatusGeneral;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payment")
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "paymentID", unique = true, nullable = false)
  private Integer paymentID;

  @ManyToOne
  @JoinColumn(name = "customerID", referencedColumnName = "customerID", nullable = false, insertable = false, updatable = false)
  @JsonBackReference
  private Customer customerPayment;

  @Column(name = "createTime", unique = false, nullable = true)
  @Temporal(TemporalType.DATE)
  private Date createTime;

  @Column(name = "value", unique = false, nullable = true)
  private Double value;

  @Column(name = "status", unique = false, nullable = false)
  @Enumerated(EnumType.STRING)
  private StatusGeneral status;
}
