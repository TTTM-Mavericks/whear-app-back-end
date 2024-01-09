package com.tttm.Whear.App.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "hashtag")
public class Hashtag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer hashtagID;
  private String hashtag;
  @Column(name = "postID", unique = true, nullable = false)
  private Integer postID;
  @ManyToOne
  @JoinColumn(name = "postID", referencedColumnName = "postID", nullable = false, insertable = false, updatable = false)
  private Post post;
}
