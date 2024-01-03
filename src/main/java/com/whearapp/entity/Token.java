package com.whearapp.entity;

import com.whearapp.enums.TokenType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "token")
public class Token {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int tokenID;
  private LocalDateTime expired;
  private LocalDateTime revoked;
  private String token;
  private TokenType tokenType;
  @JoinColumn(name = "userID")
  @ManyToOne
  private Users userID;
}
