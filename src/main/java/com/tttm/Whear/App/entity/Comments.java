package com.tttm.Whear.App.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comments {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "commentID", unique = true, nullable = false)
  private Integer commentID;

  @ManyToOne(targetEntity = User.class)
  private String userID;
//  @ManyToOne
//  @JoinColumn(name = "userID", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
//  @JsonBackReference
//  private User userComments;

  @ManyToOne(targetEntity = Post.class)
  private Integer postID;
//  @ManyToOne
//  @JoinColumn(name = "postID", referencedColumnName = "postID", nullable = false, insertable = false, updatable = false)
//  @JsonBackReference
//  private Posts postComments;

  @Column(name = "content", unique = false, nullable = false)
  private String content;
}
