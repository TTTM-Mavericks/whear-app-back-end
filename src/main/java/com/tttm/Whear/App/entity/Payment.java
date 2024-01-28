package com.tttm.Whear.App.entity;

import com.tttm.Whear.App.entity.common.AuditEntity;
import com.tttm.Whear.App.enums.StatusGeneral;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payment")
@EntityListeners(AuditingEntityListener.class)
public class Payment extends AuditEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "paymentID")
  private Integer paymentID;

  @Column(name = "customerID")
  private String customerID;
  @ManyToOne
  @JoinColumn(name = "customerID", referencedColumnName = "customerID", nullable = false, insertable = false, updatable = false)
  private Customer customer;

  @Column(name = "createTime", unique = false, nullable = true)
  @Temporal(TemporalType.DATE)
  private Date createTime;

  @Column(name = "value", unique = false, nullable = true)
  private Double value;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private StatusGeneral status;
}
