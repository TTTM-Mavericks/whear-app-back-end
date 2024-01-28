package com.tttm.Whear.App.utils.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tttm.Whear.App.entity.Hashtag;
import com.tttm.Whear.App.enums.StatusGeneral;
import com.tttm.Whear.App.enums.TypeOfPosts;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

  private Integer postID;
  private String userID;
  private TypeOfPosts typeOfPosts;
  private List<Hashtag> hashtag;
  private String date;
  private StatusGeneral status;
}
