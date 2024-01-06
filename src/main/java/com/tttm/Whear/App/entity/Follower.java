package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "follower")
public class Follower {
    @EmbeddedId
    private FollowerKey followerKey;

    @Embeddable
    public class FollowerKey implements Serializable
    {
        @ManyToOne
        @JoinColumn(name = "followerUserID", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
        @JsonBackReference
        private User followerUserID;

        @ManyToOne
        @JoinColumn(name = "followingUserID", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
        @JsonBackReference
        private User followingUserID;
    }
}
