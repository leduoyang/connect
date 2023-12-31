package com.connect.data.repository.impl;

import com.connect.common.enums.FollowStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.data.dao.IFollowDao;
import com.connect.data.entity.Follow;
import com.connect.data.repository.IFollowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class FollowRepositoryImpl implements IFollowRepository {
    @Autowired
    IFollowDao followDao;

    public FollowRepositoryImpl(IFollowDao followDao) {
        this.followDao = followDao;
    }

    public void createFollow(Follow follow) {
        log.info(String.format("follower: %s, following %s", follow.getFollowerId(), follow.getFollowingId()));

        if (followDao.isFollowing(follow.getFollowerId(), follow.getFollowingId())) {
            throw new ConnectDataException(ConnectErrorCode.FOLLOW_EXISTED_EXCEPTION);
        }

        int affected = followDao.createFollow(follow);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.FOLLOW_CREATE_EXCEPTION);
        }
    }

    public void updateFollow(Follow follow) {
        log.info(String.format("follower: %s, following %s", follow.getFollowerId(), follow.getFollowingId()));

        if (follow.getFollowerId() != null &&
                !followDao.isFollowing(follow.getFollowerId(), follow.getFollowingId())) {
            throw new ConnectDataException(ConnectErrorCode.FOLLOW_NOT_EXISTED_EXCEPTION);
        }

        int affected = followDao.updateFollow(follow);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.FOLLOW_UPDATE_EXCEPTION);
        }
    }

    public boolean isFollowing(long followerId, long followingId) {
        return followDao.isFollowing(followerId, followingId);
    }

    public boolean isFollowing(long followerId, long followingId, int status) {
        return followDao.isFollowingWithTargetStatus(followerId, followingId, status);
    }

    public int countFollower(long followingId) {
        return followDao.countFollower(followingId);
    }

    public int countFollowing(long followerId) {
        return followDao.countFollowing(followerId);
    }

    public List<Long> queryFollowerIdList(long followingId) {
        return followDao.queryFollowerIdList(followingId);
    }

    public List<Long> queryFollowingIdList(long followerId) {
        return followDao.queryFollowingIdList(followerId);
    }

    public List<Long> queryPendingIdList(long followingId) {
        return followDao.queryPendingIdList(followingId);
    }
}
