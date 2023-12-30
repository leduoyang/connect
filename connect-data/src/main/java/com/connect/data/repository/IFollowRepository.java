package com.connect.data.repository;

import com.connect.data.entity.Follow;

import java.util.List;

public interface IFollowRepository {
    void createFollow(Follow follow);

    void updateFollow(Follow follow);

    boolean isFollowing(long followerId, long followingId);

    boolean isFollowing(long followerId, long followingId, int status);

    int countFollower(long followingId);

    int countFollowing(long followerId);

    List<Long> queryFollowerIdList(long followingId);

    List<Long> queryFollowingIdList(long followerId);

    List<Long> queryPendingIdList(long followingId);
}
