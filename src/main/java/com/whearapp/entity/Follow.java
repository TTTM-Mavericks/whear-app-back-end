package com.whearapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import java.io.Serializable;
import org.springframework.data.annotation.Reference;

@Entity(name = "follow")
public class Follow {
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  private int followID;
//  @JoinColumn
//  @ManyToOne
//  private Users followerUserID;
//  @JoinColumn
//  @ManyToOne
//  private Users followingUserID;
  @EmbeddedId
  private FollowKey followKey;

}
@Embeddable
class FollowKey implements Serializable {
  @JoinColumn(name = "followerUserID")
  @ManyToOne
  private Users followerUserID;
  @JoinColumn(name = "followingUserID")
  @ManyToOne
  private Users followingUserID;
}