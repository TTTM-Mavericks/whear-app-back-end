package com.tttm.Whear.App.entity;

import com.tttm.Whear.App.enums.ColorType;
import com.tttm.Whear.App.enums.SizeType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "clothes_color")
public class ClothesColor {
    @Id
    @Column(name = "clothesID", unique = true, nullable = false)
    private Integer clothesID;

    @ManyToOne
    @MapsId
    @JoinColumn(name = "clothesID", referencedColumnName = "clothesID", nullable = false, insertable = false, updatable = false)
    private Clothes clothesColor;

    @Column(name = "sizeType", unique = false, nullable = true)
    private SizeType sizeType;
}
