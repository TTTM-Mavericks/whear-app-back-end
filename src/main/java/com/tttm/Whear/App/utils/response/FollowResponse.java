package com.tttm.Whear.App.utils.response;

import com.tttm.Whear.App.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowResponse {
    private UserResponse followerUser;
    private UserResponse followingUser;
}
