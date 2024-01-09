package com.tttm.Whear.App.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

  @Column(name = "userID", unique = true, nullable = false)
  private String userID;
  @ManyToOne
  @JoinColumn(name = "userID", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
  private User user;

  @Column(name = "postID", unique = true, nullable = false)
  private Integer postID;
  @ManyToOne
  @JoinColumn(name = "postID", referencedColumnName = "postID", nullable = false, insertable = false, updatable = false)
  private Post post;

  @Column(name = "content", unique = false, nullable = false)
  private String content;
}
