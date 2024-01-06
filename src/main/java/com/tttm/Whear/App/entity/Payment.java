package com.tttm.Whear.App.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.tttm.Whear.App.enums.StatusGeneral;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paymentID", unique = true, nullable = false)
    private Integer paymentID;

    @ManyToOne
    @JoinColumn(name = "customerID", referencedColumnName = "customerID", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private Customer customerPayment;

    @Column(name = "createTime", unique = false, nullable = true)
    @Temporal(TemporalType.DATE)
    private Date createTime;

    @Column(name = "value", unique = false, nullable = true)
    private Double value;

    @Column(name = "status", unique = false, nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusGeneral status;
}
