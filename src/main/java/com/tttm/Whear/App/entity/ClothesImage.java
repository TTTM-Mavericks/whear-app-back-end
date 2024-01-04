package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "clothes_image")
public class ClothesImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imgID", unique = true, nullable = false)
    private Integer imgID;

    @Column(name = "imageUrl", unique = false, nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "clothID", referencedColumnName = "clothesID", nullable = false)
    @JsonBackReference
    private Clothes images;
}
