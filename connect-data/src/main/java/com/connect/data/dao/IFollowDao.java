package com.connect.data.dao;

import com.connect.data.entity.Follow;
import com.connect.data.entity.Star;

import java.util.List;

public interface IFollowDao {
    int createFollow(Follow follow);

    int updateFollow(Follow follow);

    boolean isFollowing(String followerId, String followingId);

    boolean isFollowingWithTargetStatus(String followerId, String followingId, int status);

    int countFollower(String followingId);

    int countFollowing(String followerId);

    List<String> queryFollowerIdList(String followingId);

    List<String> queryFollowingIdList(String followerId);

    List<String> queryPendingIdList(String followerId);

    int approve(String followingId, String followerId);

    int reject(String followingId, String followerId);

    int remove(String followingId, String followerId);

    int approveAll(String followingId);
}
