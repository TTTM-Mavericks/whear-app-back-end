package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FollowerKey implements Serializable {
  @Column(name = "followerUserID", unique = true, nullable = false)
  private String followerUserID;
  @ManyToOne
  @JoinColumn(name = "followerUserID", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
  private User followerUser;

  @Column(name = "followingUserID", unique = true, nullable = false)
  private String followingUserID;
  @ManyToOne
  @JoinColumn(name = "followingUserID", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
  private User followingUser;
}