package com.tttm.Whear.App.entity;

import com.tttm.Whear.App.enums.ENotificationAction;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "notification")
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer notiID;
  private String baseUserID;
  @ManyToOne
  @JoinColumn(name = "baseUserID", referencedColumnName = "userID", insertable = false, updatable = false)
  private User baseUser;

  private String targetUserID;
  @ManyToOne
  @JoinColumn(name = "targetUserID", referencedColumnName = "userID", insertable = false, updatable = false)
  private User targetUser;

  public enum MessageType {
    CHAT,
    JOIN,
    LEAVE
  }

  private MessageType type;
  private String content;
  private String sender;
  private ENotificationAction action;
  private Integer actionID;
  private String message;
  private Boolean status;
  private LocalDateTime dateTime;
}
