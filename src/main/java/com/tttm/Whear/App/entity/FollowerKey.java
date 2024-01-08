package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  @ManyToOne
  @JoinColumn(name = "followerUserID", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
  @JsonBackReference
  private User followerUserID;

  @ManyToOne
  @JoinColumn(name = "followingUserID", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
  @JsonBackReference
  private User followingUserID;
}