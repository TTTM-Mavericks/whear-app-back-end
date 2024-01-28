package com.tttm.Whear.App.entity;

import com.tttm.Whear.App.entity.common.AuditEntity;
import com.tttm.Whear.App.enums.ESubRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subRole")
@EntityListeners(AuditingEntityListener.class)
public class SubRole extends AuditEntity implements Serializable {
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
