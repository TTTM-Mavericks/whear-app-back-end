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
//  @JoinColumn(name = "follower_userid")
  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "follower_userid", referencedColumnName = "username")
//  @PrimaryKeyJoinColumn(name = "follower_userid")
  private User follower_userid;
//  @ManyToOne
//  @JoinColumn(name = "follower_userid", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
//  @JsonBackReference
//  private User followerUser;

//  @ManyToOne
  @ManyToOne(targetEntity = User.class)
  @JoinColumn(name = "following_userid", referencedColumnName = "username")
  private User following_userid;
//  @ManyToOne
//  @JoinColumn(name = "following_userid", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
//  @JsonBackReference
//  private User followingUser;
}