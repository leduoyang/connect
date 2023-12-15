package com.connect.core.service.follow;

import com.connect.api.follow.dto.FollowDto;
import com.connect.api.follow.dto.UnFollowDto;

public interface IFollowService {
    void follow(FollowDto request);

    void unfollow(UnFollowDto request);

    boolean followExisting(String followerId, String followingId, Boolean isActive);
}
