package com.connect.data.repository.impl;

import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.data.dao.IStarDao;
import com.connect.data.entity.Star;
import com.connect.data.repository.IStarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class StarRepositoryImpl implements IStarRepository {
    @Autowired
    IStarDao starDao;

    public StarRepositoryImpl(IStarDao starDao) {
        this.starDao = starDao;
    }

    public void createStar(Star star) {
        if (starDao.starExisting(star.getUserId(), star.getTargetId(), star.getTargetType())) {
            throw new ConnectDataException(ConnectErrorCode.STAR_EXISTED_EXCEPTION);
        }

        int affected = starDao.createStar(star);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.STAR_CREATE_EXCEPTION);
        }
    }

    public void updateStar(Star star) {
        if (!starDao.starExisting(star.getUserId(), star.getTargetId(), star.getTargetType())) {
            throw new ConnectDataException(ConnectErrorCode.STAR_NOT_EXISTED_EXCEPTION);
        }

        int affected = starDao.updateStar(star);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.STAR_UPDATE_EXCEPTION);
        }
    }

    public boolean starExisting(long userId, long targetId, int targetType) {
        return starDao.starExisting(userId, targetId, targetType);
    }

    public boolean starExisting(long userId, long targetId, int targetType, Boolean isActive) {
        return starDao.starExistingWithTargetStatus(userId, targetId, targetType, isActive);
    }

    public int countStars(long targetId, int targetType) {
        return starDao.countStars(targetId, targetType);
    }

    public List<Integer> queryTargetIdList(int targetType, long userId) {
        return starDao.queryTargetIdList(targetType, userId);
    }
}
