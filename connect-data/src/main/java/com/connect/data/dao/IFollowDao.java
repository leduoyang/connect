package com.connect.data.dao;

import com.connect.data.entity.Follow;
import com.connect.data.entity.Star;

import java.util.List;

public interface IFollowDao {
    int createFollow(Follow follow);

    int updateFollow(Follow follow);

    boolean followExisting(String followerId, String followingId);

    boolean followExistingWithTargetStatus(String followerId, String followingId, Boolean isActive);

    int countFollower(String followingId);

    int countFollowing(String followerId);

    List<String> queryFollowerIdList(String followingId);

    List<String> queryFollowingIdList(String followerId);
}
