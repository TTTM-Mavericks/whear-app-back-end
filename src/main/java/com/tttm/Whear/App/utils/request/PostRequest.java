package com.tttm.Whear.App.utils.request;

import com.tttm.Whear.App.enums.StatusGeneral;
import com.tttm.Whear.App.enums.TypeOfPosts;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequest {

  private Integer postID;
  private String userID;
  private TypeOfPosts typeOfPosts;
  private String hashtag;
  private Date date;
  private StatusGeneral status;
}
