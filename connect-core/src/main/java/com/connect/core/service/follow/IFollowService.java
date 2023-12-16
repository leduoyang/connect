package com.connect.core.service.follow;

import com.connect.api.follow.dto.FollowDto;
import com.connect.api.follow.dto.UnFollowDto;
import com.connect.common.enums.FollowStatus;

public interface IFollowService {
    void follow(FollowDto request);

    void unfollow(UnFollowDto request);

    boolean isFollowing(String followerId, String followingId, FollowStatus status);

    void approve(String followingId, String followerId);

    void reject(String followingId, String followerId);

    void remove(String followingId, String followerId);

    void approveAll(String followingId);
}
