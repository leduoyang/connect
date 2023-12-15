package com.connect.core.service.follow;

import com.connect.api.follow.dto.FollowDto;
import com.connect.api.follow.dto.UnFollowDto;
import com.connect.common.enums.FollowStatus;

public interface IFollowService {
    void follow(FollowDto request);

    void unfollow(UnFollowDto request);

    boolean followExisting(String followerId, String followingId, FollowStatus status);
}
