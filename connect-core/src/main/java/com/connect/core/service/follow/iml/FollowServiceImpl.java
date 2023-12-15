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
        if (userRepository.queryUserByUserId(request.getFollowerId()) == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "follower not existed: " + request.getFollowingId()
            );
        }
        User targetUser = userRepository.queryUserByUserId(request.getFollowingId());
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

        if (followRepository.followExisting(follow.getFollowerId(), follow.getFollowingId())) {
            followRepository.updateFollow(follow);
        } else {
            followRepository.createFollow(follow);
        }

        updateFollowingCount(request.getFollowerId());
        updateFollowerCount(request.getFollowingId());
    }

    @Override
    public void unfollow(UnFollowDto request) {
        Follow follow = new Follow()
                .setFollowerId(request.getFollowerId())
                .setFollowingId(request.getFollowingId())
                .setStatus(FollowStatus.UNFOLLOW.getCode());

        if (followRepository.followExisting(follow.getFollowerId(), follow.getFollowingId())) {
            followRepository.updateFollow(follow);
        } else {
            followRepository.createFollow(follow);
        }

        updateFollowingCount(request.getFollowerId());
        updateFollowerCount(request.getFollowingId());
    }

    @Override
    public boolean followExisting(String followerId, String followingId, FollowStatus status) {
        return followRepository.followExisting(
                followerId,
                followingId,
                status.getCode()
        );
    }

    @Override
    public void approve(String followingId, String followerId) {
        followRepository.approve(followingId, followerId);
    }

    @Override
    public void reject(String followingId, String followerId) {
        followRepository.reject(followingId, followerId);
    }

    @Override
    public void remove(String followingId, String followerId) {
        followRepository.remove(followingId, followerId);
    }

    @Override
    public void approveAll(String followingId) {
        followRepository.approveAll(followingId);
    }

    private void updateFollowingCount(String followerId) {
        int followings = followRepository.countFollowing(followerId);
        User user = userRepository.queryUserByUserId(followerId);
        user.setFollowings(followings);
        userRepository.refreshFollowings(
                user.getUserId(),
                user.getVersion(),
                user.getFollowings()
        );
    }

    private void updateFollowerCount(String followingId) {
        int followers = followRepository.countFollower(followingId);
        User user = userRepository.queryUserByUserId(followingId);
        user.setFollowings(followers);
        userRepository.refreshFollowers(
                user.getUserId(),
                user.getVersion(),
                user.getFollowings()
        );
    }
}
