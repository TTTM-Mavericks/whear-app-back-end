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

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "comments")
public class Comments implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "commentID")
  private Integer commentID;

  @Column(name = "userID")
  private String userID;
  @ManyToOne
  @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false, insertable = false, updatable = false)
  private User user;

  @Column(name = "postID")
  private Integer postID;
  @ManyToOne
  @JoinColumn(name = "postID", referencedColumnName = "postID", nullable = false, insertable = false, updatable = false)
  private Post post;

  @Column(name = "content")
  private String content;
}
