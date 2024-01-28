package com.tttm.Whear.App.entity;

import com.tttm.Whear.App.entity.common.AuditEntity;
import jakarta.persistence.*;

import java.io.Serializable;

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
@Table(name = "brand")
@EntityListeners(AuditingEntityListener.class)
public class Brand extends AuditEntity implements Serializable {

  @Id
  @Column(name = "brandID")
  private String brandID;

  @OneToOne
  @MapsId
  @JoinColumn(name = "brandID", referencedColumnName = "customerID", nullable = false, insertable = false, updatable = false)
  private Customer customer;

  @Column(name = "description")
  private String description;

  @Column(name = "address")
  private String address;

  @Column(name = "link")
  private String link;
}
