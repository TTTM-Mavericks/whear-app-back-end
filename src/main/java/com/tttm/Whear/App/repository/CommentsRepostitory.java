package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.Comments;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepostitory extends JpaRepository<Comments, Integer> {

  List<Comments> getAllByPostID(Integer postID);
}
