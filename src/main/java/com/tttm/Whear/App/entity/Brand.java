package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "brand")
public class Brand {
    @Id
    @Column(name = "brandID", unique = true, nullable = false)
    private String brandID;

    @OneToOne
    @MapsId
    @JoinColumn(name = "brandID", referencedColumnName = "customerID", nullable = false, insertable = false, updatable = false)
    private Customer customer;

    @Column(name = "description", unique = false, nullable = false)
    private String description;

    @Column(name = "address", unique = false, nullable = false)
    private String address;

    @Column(name = "link", unique = false, nullable = false)
    private String link;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<News> BrandPublishNews; // brand publish news about their product
}
