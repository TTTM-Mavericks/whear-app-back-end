package com.tttm.Whear.App.entity;

import com.tttm.Whear.App.entity.common.AuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "memory_entity")
@EntityListeners(AuditingEntityListener.class)
public class MemoryEntity extends AuditEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memoryID")
    private Integer memoryID;

    // Style Memory
    @Column(name = "styleID")
    private Integer styleID;

    // BodyShape Memory
    @Column(name = "bodyShapeID")
    private Integer bodyShapeID;

    // Top Memory store ID in type String of Clothes Entity not Integer because it can be null
    @Column(name = "topInsideID", nullable = true)
    private String topInsideID;

    @Column(name = "topInsideColor", nullable = true)
    private String topInsideColor;

    @Column(name = "topOutsideID", nullable = true)
    private String topOutsideID;

    @Column(name = "topOutsideColor", nullable = true)
    private String topOutsideColor;

    @Column(name = "topMaterial", nullable = true)
    private String topMaterial;

    // Bottom Memory store ID in type String of Clothes Entity not Integer because it can be null
    @Column(name = "bottomKindID", nullable = true)
    private String bottomKindID;

    @Column(name = "bottomColor", nullable = true)
    private String bottomColor;

    @Column(name = "shoesTypeID", nullable = true)
    private String shoesTypeID;

    @Column(name = "shoesTypeColor", nullable = true)
    private String shoesTypeColor;

    @Column(name = "bottomMaterial", nullable = true)
    private String bottomMaterial;

    // Accessories store ID in type String of Clothes Entity not Integer because it can be null
    @Column(name = "accessoryKindID", nullable = true)
    private String accessoryKindID;

    @Column(name = "accessoryMaterial", nullable = true)
    private String accessoryMaterial;

    // Store user
    @Column(name = "dislikeClothesByUser", nullable = true)
    private String dislikeClothesByUser;

    @Column(name = "suggestClothesToUser", nullable = true)
    private String suggestClothesToUser;
}
