package com.connect.core.service.follow;

import com.connect.api.follow.dto.FollowDto;
import com.connect.api.follow.dto.UnFollowDto;
import com.connect.common.enums.FollowStatus;

import java.util.List;

public interface IFollowService {
    void follow(FollowDto request);

    void unfollow(FollowDto request);

    boolean isFollowing(String followerId, String followingId, FollowStatus status);

    void approve(FollowDto request);

    void reject(FollowDto request);

    void remove(FollowDto request);

    void approveAll(FollowDto request);

    List<String> queryPendingIdList(String followingId);
}
