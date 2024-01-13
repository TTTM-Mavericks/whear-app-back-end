package com.tttm.Whear.App.entity;

import com.tttm.Whear.App.enums.ESubRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subRole")
public class SubRole implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer subRoleID;
  @Column(name = "subRoleName")
  private ESubRole subRoleName;
  @Column(name = "numberOfCollection")
  private Integer numberOfCollection;
  @Column(name = "numberOfClothes")
  private Integer numberOfClothes;
}
