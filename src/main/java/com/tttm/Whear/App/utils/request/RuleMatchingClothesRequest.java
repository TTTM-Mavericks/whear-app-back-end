package com.tttm.Whear.App.utils.request;

import com.tttm.Whear.App.enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.N;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RuleMatchingClothesRequest {
    private String styleType;

    private String bodyShapeType;

    // Top Kind, Materials and Color Rule
    private String topInside;

    private String topInsideColor;

    private String topOutside;

    private String topOutsideColor;

    private String topMaterial;

    // Bottom Kind, Materials and Color Rule
    private String bottomKind;

    private String bottomColor;

    private String shoesType;

    private String shoesTypeColor;

    private String bottomMaterial;

    // Accessories Materials and Kind Rule
    private String accessoryKind;

    private String accessoryMaterial;
}
