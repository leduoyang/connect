package com.connect.core.service.follow;

import com.connect.api.common.RequestMetaInfo;

import java.util.List;

public interface IFollowService {
    void follow(String following, RequestMetaInfo requestMetaInfo);

    void unfollow(String following, RequestMetaInfo requestMetaInfo);

    boolean isFollowing(String following, RequestMetaInfo requestMetaInfo);

    void approve(String follower, RequestMetaInfo requestMetaInfo);

    void reject(String follower, RequestMetaInfo requestMetaInfo);

    void remove(String follower, RequestMetaInfo requestMetaInfo);

    void approveAll(RequestMetaInfo requestMetaInfo);

    List<Long> queryPendingIdList(RequestMetaInfo requestMetaInfo);
}
