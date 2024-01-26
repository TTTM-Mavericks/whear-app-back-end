package com.tttm.Whear.App.utils.request;

import com.tttm.Whear.App.entity.Notification;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationRequest {

  private String notiID;
  private String baseUserID;
  private String targetUserID;

  public enum MessageType {
    CHAT,
    JOIN,
    LEAVE
  }

  private Notification.MessageType type;
  private String content;
  private String sender;

  /**
   * FOLLOW, UNFOLLOW, REACT, POSTING
   */
  private String action;

  /**
   * id of above action
   */
  private Integer actionID;
  private String message;
  private String status;
  private LocalDateTime dateTime;
}
