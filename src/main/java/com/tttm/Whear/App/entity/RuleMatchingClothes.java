package com.tttm.Whear.App.entity;

import com.tttm.Whear.App.entity.common.AuditEntity;
import com.tttm.Whear.App.enums.*;
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
@Table(name = "rule_matching_clothes")
@EntityListeners(AuditingEntityListener.class)
public class RuleMatchingClothes extends AuditEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ruleid")
    private Integer ruleID;

    @Column(name = "style_type", nullable = false)
    private String styleType;

    @Column(name = "body_shape_type", nullable = false)
    private String bodyShapeType;

    // Top Kind, Materials and Color Rule
    @Column(name = "top_inside", nullable = true)
    private String topInside;

    @Column(name = "top_inside_color", nullable = true)
    private String topInsideColor;

    @Column(name = "top_outside", nullable = true)
    private String topOutside;

    @Column(name = "top_outside_color", nullable = true)
    private String topOutsideColor;

    @Column(name = "top_material", nullable = true)
    private String topMaterial;

    // Bottom Kind, Materials and Color Rule
    @Column(name = "bottom_kind", nullable = true)
    private String bottomKind;

    @Column(name = "bottom_color", nullable = true)
    private String bottomColor;

    @Column(name = "shoes_type", nullable = true)
    private String shoesType;

    @Column(name = "shoes_type_color", nullable = true)
    private String shoesTypeColor;

    @Column(name = "bottom_material", nullable = true)
    private String bottomMaterial;

    // Accessories Materials and Kind Rule
    @Column(name = "accessory_kind", nullable = true)
    private String accessoryKind;

    @Column(name = "accessory_material", nullable = true)
    private String accessoryMaterial;
}
