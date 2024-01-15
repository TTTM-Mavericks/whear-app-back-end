package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tttm.Whear.App.enums.StatusGeneral;
import com.tttm.Whear.App.enums.TypeOfPosts;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "posts")
public class Post implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "postID", unique = true, nullable = false)
  private Integer postID;

  @Column(name = "userID", unique = true, nullable = false)
  private String userID;
  @ManyToOne
  @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false, insertable = false, updatable = false)
  private User user;

  @Column(name = "typeOfPost", unique = false, nullable = false)
  @Enumerated(EnumType.STRING)
  private TypeOfPosts typeOfPosts;

  @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<Hashtag> hashtag;

  @Column(name = "date", unique = false, nullable = false)
  @Temporal(TemporalType.DATE)
  private Date date;

  @Column(name = "status", unique = false, nullable = false)
  private StatusGeneral status;
}
