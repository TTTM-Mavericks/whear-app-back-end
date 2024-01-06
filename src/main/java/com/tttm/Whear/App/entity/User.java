package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tttm.Whear.App.enums.Language;
import com.tttm.Whear.App.enums.StatusGeneral;
import com.tttm.Whear.App.enums.ERole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", unique = false, nullable = false)
    private String password;

    @Column(name = "dateOfBirth", unique = false, nullable = true)
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "phone", unique = true, nullable = false)
    private String phone;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "gender", unique = false, nullable = true)
    private Boolean gender;

    @Column(name = "role", unique = false, nullable = true)
    @Enumerated(EnumType.STRING)
    private ERole role;

    @Column(name = "imgUrl", unique = false, nullable = true)
    private String imgUrl;

    @Column(name = "status", unique = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusGeneral status;

    @Column(name = "language", unique = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private Language language;

    @OneToMany(mappedBy = "followerKey.followerUserID", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Follower> followList;

    @OneToMany(mappedBy = "followerKey.followingUserID", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Follower> followingList;

    @OneToMany(mappedBy = "userToken", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Token> tokenList;

    @OneToMany(mappedBy = "userPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Posts> userPostList;

    @OneToMany(mappedBy = "userCollection", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Collection> userCollectionList;

    @OneToMany(mappedBy = "userPostReactKey.userReact", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<React> userReact;

    @OneToMany(mappedBy = "userComments", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comments> userComments;

}
