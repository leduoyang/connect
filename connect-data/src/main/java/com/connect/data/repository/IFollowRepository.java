package com.connect.data.repository;

import com.connect.data.entity.Follow;

import java.util.List;

public interface IFollowRepository {
    void createFollow(Follow follow);

    void updateFollow(Follow follow);

    boolean followExisting(String followerId, String followingId);

    boolean followExisting(String followerId, String followingId, Boolean isActive);

    int countFollower(String followingId);

    int countFollowing(String followerId);

    List<String> queryFollowerIdList(String followingId);

    List<String> queryFollowingIdList(String followerId);
}
