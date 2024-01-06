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
@Table(name = "react")
public class React {
    @EmbeddedId
    private UserPostReactKey userPostReactKey;

    @Column(name = "react", unique = false, nullable = true)
    private String react;
    @Embeddable
    public class UserPostReactKey implements Serializable
    {
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
