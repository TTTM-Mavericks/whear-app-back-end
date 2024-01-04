package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tttm.Whear.App.Enums.StatusGeneral;
import com.tttm.Whear.App.Enums.TypeOfNews;
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
@Table(name = "news")
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "newsID", unique = true, nullable = false)
    private Integer newsID;

    @ManyToOne
    @JoinColumn(name = "brandID", referencedColumnName = "brandID", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private Brand brand;

    @Column(name = "title", unique = false, nullable = false)
    private String title;

    @Column(name = "content", unique = false, nullable = false)
    private String content;

    @Column(name = "typeOfNews", unique = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeOfNews typeOfNews;

    @Column(name = "status", unique = false, nullable = false)
    private StatusGeneral status;

    @OneToMany(mappedBy = "images", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<NewsImages> newsImageList;
}
