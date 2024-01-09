package com.tttm.Whear.App.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "react")
public class React {

  @EmbeddedId
  private UserPostReactKey userPostReactKey;

  @Column(name = "react", unique = false, nullable = true)
  private String react;

  @Embeddable
  public class UserPostReactKey implements Serializable {

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
  }
}
