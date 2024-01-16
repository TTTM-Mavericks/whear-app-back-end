package com.tttm.Whear.App.repository;

import com.tttm.Whear.App.entity.Follower;
import com.tttm.Whear.App.entity.FollowerKey;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowerRepository extends JpaRepository<Follower, FollowerKey> {

  @Query(value = "Select f.* from Follower f where f.follower_userid = ?1", nativeQuery = true)
  List<Follower> findAllFollowerUserByUserID(String userid);

  @Query(value = "Select f.* from Follower f where f.following_userid = ?1", nativeQuery = true)
  List<Follower> findAllFollowingUserByUserID(String userID);
  @Modifying
  @Transactional
  @Query(value = "Select f.* from Follower f where f.follower_userid = ?1 and f.following_userid = ?2", nativeQuery = true)
  List<Follower> findFollowerByFollowerIdAndFollowingId(String followerUserID, String followingUserID);
  @Modifying
  @Transactional
  @Query(value = "insert into follower (follower_userid,following_userid) values (?1, ?2)", nativeQuery = true)
  void insertFollower(String followerUserID, String followingUserID);

   @Modifying
   @Transactional
   @Query(value = "delete from follower where follower_userid = ?1 and following_userid = ?2", nativeQuery = true)
   void deleteFollowerByFollowerIDandFollowingID(String followerUserID, String followingUserID);
}
