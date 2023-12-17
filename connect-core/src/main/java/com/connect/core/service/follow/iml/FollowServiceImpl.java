package com.connect.core.service.follow.iml;

import com.connect.api.follow.dto.FollowDto;
import com.connect.api.follow.dto.UnFollowDto;
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
    public void follow(FollowDto request) {
        if (userRepository.internalQueryUserByUserId(request.getFollowerId()) == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "follower not existed: " + request.getFollowingId()
            );
        }
        User targetUser = userRepository.internalQueryUserByUserId(request.getFollowingId());
        if (targetUser == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "following userId not existed: " + request.getFollowingId()
            );
        }
        UserStatus status = UserStatus.getStatus(targetUser.getStatus());
        if (status.equals(UserStatus.PRIVATE)
                || status.equals(UserStatus.DELETED)
        ) {
            throw new ConnectDataException(
                    ConnectErrorCode.ILLEGAL_REQUESTER_ERROR,
                    "target userId not available for following: " + request.getFollowingId()
            );
        }

        Follow follow = new Follow()
                .setFollowerId(request.getFollowerId())
                .setFollowingId(request.getFollowingId());
        if (status.equals(UserStatus.SEMI)) {
            follow.setStatus(FollowStatus.PENDING.getCode());
        } else {
            follow.setStatus(FollowStatus.APPROVED.getCode());
        }

        updateTargetStatus(follow);
    }

    @Override
    public void unfollow(FollowDto request) {
        String followerId = request.getFollowerId();
        String followingId = request.getFollowingId();

        if (!followRepository.isFollowing(followerId, followingId, FollowStatus.APPROVED.getCode())) {
            throw new ConnectDataException(
                    ConnectErrorCode.FOLLOW_NOT_EXISTED_EXCEPTION,
                    String.format("UNFOLLOW FAILED! %s is not following %s", followerId, followingId)
            );
        }

        Follow follow = new Follow()
                .setFollowerId(followerId)
                .setFollowingId(followingId)
                .setStatus(FollowStatus.UNFOLLOW.getCode());
        updateTargetStatus(follow);
    }

    @Override
    public boolean isFollowing(String followerId, String followingId, FollowStatus status) {
        return followRepository.isFollowing(
                followerId,
                followingId,
                status.getCode()
        );
    }

    @Override
    public void approve(FollowDto request) {
        String followerId = request.getFollowerId();
        String followingId = request.getFollowingId();

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
    public void reject(FollowDto request) {
        String followerId = request.getFollowerId();
        String followingId = request.getFollowingId();

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
    public void remove(FollowDto request) {
        String followerId = request.getFollowerId();
        String followingId = request.getFollowingId();

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
    public void approveAll(FollowDto request) {
        String followingId = request.getFollowingId();

        List<String> pendingList = followRepository.queryPendingIdList(followingId);
        if (pendingList == null || pendingList.size() == 0) {
            throw new ConnectDataException(
                    ConnectErrorCode.FOLLOW_NOT_EXISTED_EXCEPTION,
                    String.format("nothing to approve for user %s", followingId)
            );
        }

        Follow follow = new Follow()
                .setFollowingId(request.getFollowingId())
                .setStatus(FollowStatus.APPROVED.getCode());
        followRepository.updateFollow(follow);
    }

    private void updateTargetStatus(Follow follow) {
        String followerId = follow.getFollowerId();
        String followingId = follow.getFollowingId();

        if (followRepository.isFollowing(followerId, followingId)) {
            followRepository.updateFollow(follow);
        } else {
            followRepository.createFollow(follow);
        }
        updateFollowingCount(followerId);
        updateFollowerCount(followingId);
    }

    private void updateFollowingCount(String followerId) {
        int followings = followRepository.countFollowing(followerId);
        User user = userRepository.internalQueryUserByUserId(followerId);
        user.setFollowings(followings);
        userRepository.refreshFollowings(
                user.getUserId(),
                user.getVersion(),
                user.getFollowings()
        );
    }

    private void updateFollowerCount(String followingId) {
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
