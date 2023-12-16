package com.connect.data.repository;

import com.connect.data.entity.Follow;

import java.util.List;

public interface IFollowRepository {
    void createFollow(Follow follow);

    void updateFollow(Follow follow);

    boolean isFollowing(String followerId, String followingId);

    boolean isFollowing(String followerId, String followingId, int status);

    int countFollower(String followingId);

    int countFollowing(String followerId);

    List<String> queryFollowerIdList(String followingId);

    List<String> queryFollowingIdList(String followerId);

    List<String> queryPendingIdList(String followingId);
}
