package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.Follower;
import com.tttm.Whear.App.entity.FollowerKey;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, FollowerKey> {

  @Query(value = "Select f.* from Follower f where f.follower_userid = ?1", nativeQuery = true)
  List<Follower> findAllFollowerUserByUsername(String username);

  @Query(value = "Select f.* from Follower f where f.following_userid = ?1", nativeQuery = true)
  List<Follower> findAllFollowingUserByUsername(String username);

  @Query(value = "Select f.* from Follower f where f.follower_userid = ?1 and f.following_userid = ?2", nativeQuery = true)
  Follower findFollowerByFollowerIdAndFollowingId(String followerUserID, String followingUserID);
}
