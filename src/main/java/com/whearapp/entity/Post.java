package com.whearapp.entity;

import com.whearapp.enums.TypeOfPost;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "post")
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int postID;
  @JoinColumn(name = "userID")
  @ManyToOne
  private Users userID;
  private TypeOfPost typeOfPost;
  private String hashtag;
  private LocalDateTime date;
  private boolean status;
}
