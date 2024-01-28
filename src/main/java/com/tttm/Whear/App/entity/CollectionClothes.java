package com.tttm.Whear.App.entity;

import com.tttm.Whear.App.entity.common.AuditEntity;
import jakarta.persistence.*;

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
@Table(name = "collection_clothes")
@EntityListeners(AuditingEntityListener.class)
public class CollectionClothes extends AuditEntity {

  @EmbeddedId
  private CollectionClothesKey collectionClothesKey;
}
