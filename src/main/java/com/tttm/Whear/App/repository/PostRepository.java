package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.Posts;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Posts, Integer> {
  public Posts getPostsByPostID(Integer postID);
  public boolean deleteByPostID(Integer postID);
}
