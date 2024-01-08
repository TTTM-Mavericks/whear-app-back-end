package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tttm.Whear.App.enums.StatusGeneral;
import com.tttm.Whear.App.enums.TypeOfPosts;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  @Column(name = "date", unique = false, nullable = false)
  @Temporal(TemporalType.DATE)
  private Date date;

  @Column(name = "status", unique = false, nullable = false)
  private StatusGeneral status;

  @OneToMany(mappedBy = "images", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<PostImages> postImagesList;

  @OneToMany(mappedBy = "userPostReactKey.postReact", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<React> postReact;

  @OneToMany(mappedBy = "postComments", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  private List<Comments> postComments;
}
