package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "follower")
public class Follower {
    @Id
    @Column(name = "followerID", unique = true, nullable = false)
    private Integer followerID;

    @ManyToOne
    @JoinColumn(name = "followerUserID", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private User followerUserID;

    @ManyToOne
    @JoinColumn(name = "followingUserID", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private User followingUserID;
}
