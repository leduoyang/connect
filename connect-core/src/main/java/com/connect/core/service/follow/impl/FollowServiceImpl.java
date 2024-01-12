package com.connect.core.service.follow.impl;

import com.connect.api.common.RequestMetaInfo;
import com.connect.common.enums.FollowStatus;
import com.connect.common.enums.UserStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.follow.IFollowService;
import com.connect.data.entity.*;
import com.connect.data.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class FollowServiceImpl implements IFollowService {
    private IFollowRepository followRepository;

    private IUserRepository userRepository;

    public FollowServiceImpl(
            IFollowRepository followRepository,
            IUserRepository userRepository
    ) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void follow(String following, RequestMetaInfo requestMetaInfo) {
        User targetUser = userRepository.internalQueryUserByUsername(following);
        if (targetUser == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "following user not existed: " + following
            );
        }
        if (requestMetaInfo.getUserId().equals(targetUser.getUserId())) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    String.format("nested follow found")
            );
        }

        UserStatus status = UserStatus.getStatus(targetUser.getStatus());
        if (status.equals(UserStatus.PRIVATE)
                || status.equals(UserStatus.DELETED)
        ) {
            throw new ConnectDataException(
                    ConnectErrorCode.ILLEGAL_REQUESTER_ERROR,
                    "target user not available for following: " + following
            );
        }

        Follow follow = new Follow()
                .setFollowerId(requestMetaInfo.getUserId())
                .setFollowingId(targetUser.getUserId());
        if (status.equals(UserStatus.SEMI)) {
            follow.setStatus(FollowStatus.PENDING.getCode());
        } else {
            follow.setStatus(FollowStatus.APPROVED.getCode());
        }

        updateTargetStatus(follow);
    }

    @Override
    public void unfollow(String following, RequestMetaInfo requestMetaInfo) {
        long followerId = requestMetaInfo.getUserId();
        User targetUser = userRepository.internalQueryUserByUsername(following);
        if (targetUser == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "following user not existed: " + following
            );
        }
        long followingId = targetUser.getUserId();
        if (requestMetaInfo.getUserId().equals(followingId)) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    String.format("nested unfollow found")
            );
        }

        if (!followRepository.isFollowing(followerId, followingId, FollowStatus.APPROVED.getCode())) {
            throw new ConnectDataException(
                    ConnectErrorCode.FOLLOW_NOT_EXISTED_EXCEPTION,
                    String.format("UNFOLLOW FAILED! you are not following %s", followingId)
            );
        }

        Follow follow = new Follow()
                .setFollowerId(followerId)
                .setFollowingId(followingId)
                .setStatus(FollowStatus.UNFOLLOW.getCode());
        updateTargetStatus(follow);
    }

    @Override
    public boolean isFollowing(String following, RequestMetaInfo requestMetaInfo) {
        User followingUser = userRepository.internalQueryUserByUsername(following);
        if (followingUser == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "following user not existed: " + following
            );
        }

        return followRepository.isFollowing(
                requestMetaInfo.getUserId(),
                followingUser.getUserId(),
                FollowStatus.APPROVED.getCode()
        );
    }

    @Override
    public void approve(String follower, RequestMetaInfo requestMetaInfo) {
        long followingId = requestMetaInfo.getUserId();
        User targetUser = userRepository.internalQueryUserByUsername(follower);
        if (targetUser == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "following user not existed: " + follower
            );
        }
        long followerId = targetUser.getUserId();

        if (!followRepository.isFollowing(followerId, followingId, FollowStatus.PENDING.getCode())) {
            throw new ConnectDataException(
                    ConnectErrorCode.FOLLOW_NOT_EXISTED_EXCEPTION,
                    String.format("PENDING status not found for %s following %s", followerId, followingId)
            );
        }

        Follow follow = new Follow()
                .setFollowerId(followerId)
                .setFollowingId(followingId)
                .setStatus(FollowStatus.APPROVED.getCode());
        updateTargetStatus(follow);
    }

    @Override
    public void reject(String follower, RequestMetaInfo requestMetaInfo) {
        long followingId = requestMetaInfo.getUserId();
        User targetUser = userRepository.internalQueryUserByUsername(follower);
        if (targetUser == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "following user not existed: " + follower
            );
        }
        long followerId = targetUser.getUserId();

        if (!followRepository.isFollowing(followerId, followingId, FollowStatus.PENDING.getCode())) {
            throw new ConnectDataException(
                    ConnectErrorCode.FOLLOW_NOT_EXISTED_EXCEPTION,
                    String.format("PENDING status not found for %s following %s", followerId, followingId)
            );
        }

        Follow follow = new Follow()
                .setFollowerId(followerId)
                .setFollowingId(followingId)
                .setStatus(FollowStatus.REJECTED.getCode());
        updateTargetStatus(follow);
    }

    @Override
    public void remove(String follower, RequestMetaInfo requestMetaInfo) {
        long followingId = requestMetaInfo.getUserId();
        User targetUser = userRepository.internalQueryUserByUsername(follower);
        if (targetUser == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "following user not existed: " + follower
            );
        }
        long followerId = targetUser.getUserId();

        if (!followRepository.isFollowing(followerId, followingId, FollowStatus.APPROVED.getCode())) {
            throw new ConnectDataException(
                    ConnectErrorCode.FOLLOW_NOT_EXISTED_EXCEPTION,
                    String.format("REMOVE FAILED! %s is not following %s", followerId, followingId)
            );
        }

        Follow follow = new Follow()
                .setFollowerId(followerId)
                .setFollowingId(followingId)
                .setStatus(FollowStatus.UNFOLLOW.getCode());
        updateTargetStatus(follow);
    }

    @Override
    public void approveAll(RequestMetaInfo requestMetaInfo) {
        Follow follow = new Follow()
                .setFollowingId(requestMetaInfo.getUserId())
                .setStatus(FollowStatus.APPROVED.getCode());
        followRepository.updateFollow(follow);
    }

    @Override
    public List<Long> queryPendingIdList(RequestMetaInfo requestMetaInfo) {
        return followRepository.queryPendingIdList(requestMetaInfo.getUserId());
    }

    private void updateTargetStatus(Follow follow) {
        long followerId = follow.getFollowerId();
        long followingId = follow.getFollowingId();

        if (followRepository.isFollowing(followerId, followingId)) {
            followRepository.updateFollow(follow);
        } else {
            followRepository.createFollow(follow);
        }
        updateFollowingCount(followerId);
        updateFollowerCount(followingId);
    }

    private void updateFollowingCount(long followerId) {
        int followings = followRepository.countFollowing(followerId);
        User user = userRepository.internalQueryUserByUserId(followerId);
        user.setFollowings(followings);
        userRepository.refreshFollowings(
                user.getUserId(),
                user.getVersion(),
                user.getFollowings()
        );
    }

    private void updateFollowerCount(long followingId) {
        int followers = followRepository.countFollower(followingId);
        User user = userRepository.internalQueryUserByUserId(followingId);
        user.setFollowings(followers);
        userRepository.refreshFollowers(
                user.getUserId(),
                user.getVersion(),
                user.getFollowings()
        );
    }
}
