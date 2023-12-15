package com.connect.data.repository.impl;

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
        if (followDao.followExisting(follow.getFollowerId(), follow.getFollowingId())) {
            throw new ConnectDataException(ConnectErrorCode.FOLLOW_EXISTED_EXCEPTION);
        }

        int affected = followDao.createFollow(follow);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.FOLLOW_CREATE_EXCEPTION);
        }
    }

    public void updateFollow(Follow follow) {
        if (!followDao.followExisting(follow.getFollowerId(), follow.getFollowingId())) {
            throw new ConnectDataException(ConnectErrorCode.FOLLOW_NOT_EXISTED_EXCEPTION);
        }

        int affected = followDao.updateFollow(follow);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.FOLLOW_UPDATE_EXCEPTION);
        }
    }

    public boolean followExisting(String followerId, String followingId) {
        return followDao.followExisting(followerId, followingId);
    }

    public boolean followExisting(String followerId, String followingId, Boolean isActive) {
        return followDao.followExistingWithTargetStatus(followerId, followingId, isActive);
    }

    public int countFollower(String followingId) {
        return followDao.countFollower(followingId);
    }

    public int countFollowing(String followerId) {
        return followDao.countFollowing(followerId);
    }

    public List<String> queryFollowerIdList(String followingId) {
        return followDao.queryFollowerIdList(followingId);
    }

    public List<String> queryFollowingIdList(String followerId) {
        return followDao.queryFollowingIdList(followerId);
    }
}
