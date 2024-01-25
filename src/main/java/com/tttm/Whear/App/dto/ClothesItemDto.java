package com.tttm.Whear.App.dto;

import com.tttm.Whear.App.enums.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ClothesItemDto {
    private Integer clothesID;
    private String nameOfProduct;
    private ClothesType typeOfClothes;
    private ShapeType shape;
    private SeasonType seasons;
    private MaterialType materials;
    private List<SizeType> size;
    private List<ColorType> colors;
    public String sizeToString()
    {
        return this.size
                .stream()
                .map(size -> size + " ")
                .collect(Collectors.joining());
    }

    public String colorToString()
    {
        return this.colors
                .stream()
                .map(color -> color + " ")
                .collect(Collectors.joining());
    }
}
