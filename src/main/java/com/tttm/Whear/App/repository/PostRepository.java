package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

  public Post getPostsByPostID(Integer postID);

  public boolean deleteByPostID(Integer postID);
}
