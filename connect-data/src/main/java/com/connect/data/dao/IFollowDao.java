package com.connect.data.dao;

import com.connect.data.entity.Follow;
import com.connect.data.entity.Star;

import java.util.List;

public interface IFollowDao {
    int createFollow(Follow follow);

    int updateFollow(Follow follow);

    boolean isFollowing(long followerId, long followingId);

    boolean isFollowingWithTargetStatus(long followerId, long followingId, int status);

    int countFollower(long followingId);

    int countFollowing(long followerId);

    List<Long> queryFollowerIdList(long followingId);

    List<Long> queryFollowingIdList(long followerId);

    List<Long> queryPendingIdList(long followingId);

    int approve(long followingId, long followerId);

    int reject(long followingId, long followerId);

    int remove(long followingId, long followerId);

    int approveAll(long followingId);
}
