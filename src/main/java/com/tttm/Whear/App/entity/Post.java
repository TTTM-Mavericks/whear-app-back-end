package com.tttm.Whear.App.entity;

import com.tttm.Whear.App.entity.common.AuditEntity;
import com.tttm.Whear.App.enums.StatusGeneral;
import com.tttm.Whear.App.enums.TypeOfPosts;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "posts")
@EntityListeners(AuditingEntityListener.class)
public class Post extends AuditEntity implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "postID")
  private Integer postID;

  @Column(name = "userID")
  private String userID;
  @ManyToOne
  @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false, insertable = false, updatable = false)
  private User user;

  @Column(name = "typeOfPost")
  @Enumerated(EnumType.STRING)
  private TypeOfPosts typeOfPosts;

  @Column(name = "date")
  @Temporal(TemporalType.DATE)
  private Date date;

  @Column(name = "status")
  private StatusGeneral status;
}
