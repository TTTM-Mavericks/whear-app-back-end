package com.tttm.Whear.App.utils.request;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DateTimeRequest {

  private Date startDate;
  private Date endDate;
}
