package com.tttm.Whear.App.service;

import com.tttm.Whear.App.entity.Collection;
import com.tttm.Whear.App.utils.response.CollectionResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface CollectionService {
  public List<CollectionResponse> getCollectionsOfUser(String userID);
}
