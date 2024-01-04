package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tttm.Whear.App.Enums.SubRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "customerID", unique = true, nullable = false)
    private String customerID;

    @OneToOne
    @JoinColumn(name = "customerID", referencedColumnName = "username", nullable = false, insertable = false, updatable = false)
    private User user;

    @Column(name = "isFirstLogin", unique = false, nullable = false)
    private Boolean isFirstLogin;

    @Column(name = "subRole", unique = false, nullable = false)
    private SubRole subRole;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<History> historyList;
}
