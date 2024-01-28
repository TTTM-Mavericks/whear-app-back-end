package com.tttm.Whear.App.entity;

import com.tttm.Whear.App.entity.common.AuditEntity;
import jakarta.persistence.*;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "react")
@EntityListeners(AuditingEntityListener.class)
public class React extends AuditEntity implements Serializable{

  @EmbeddedId
  private UserPostReactKey userPostReactKey;

  @Column(name = "react", unique = false, nullable = true)
  private String react;

  @Embeddable
  public class UserPostReactKey implements Serializable {

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
  }
}
