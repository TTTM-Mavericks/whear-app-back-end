package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

  public Post getPostsByPostID(Integer postID);

  @Modifying
  @Transactional
  @Query(value = "delete from posts where postid = ?1", nativeQuery = true)
  public Integer deleteByPostID(Integer postID);
}
