package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne
    @JoinColumn(name = "userID", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private User userReact;

    @ManyToOne
    @JoinColumn(name = "postID", referencedColumnName = "postID", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private Posts postReact;
  }
}
