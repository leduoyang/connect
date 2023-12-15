package com.connect.core.service.follow.iml;

import com.connect.api.follow.dto.FollowDto;
import com.connect.api.follow.dto.UnFollowDto;
import com.connect.common.enums.FollowStatus;
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
        Follow follow = new Follow()
                .setFollowerId(request.getFollowerId())
                .setFollowingId(request.getFollowingId())
                .setStatus(FollowStatus.APPROVED.getCode());

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
