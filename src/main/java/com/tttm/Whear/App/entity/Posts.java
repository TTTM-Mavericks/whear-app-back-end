package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tttm.Whear.App.Enums.StatusGeneral;
import com.tttm.Whear.App.Enums.TypeOfPosts;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postID", unique = true, nullable = false)
    private Integer postID;

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private User userPost;

    @Column(name = "typeOfPost", unique = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeOfPosts typeOfPosts;

    @Column(name = "hashtag", unique = false, nullable = false)
    private String hashtag;

    @Column(name = "comments", unique = false, nullable = true)
    private String comments;

    @Column(name = "react", unique = false, nullable = true)
    private String react;

    @Column(name = "date", unique = false, nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "status", unique = false, nullable = false)
    private StatusGeneral status;

    @OneToMany(mappedBy = "images", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PostImages> postImagesList;
}
