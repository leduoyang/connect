package com.connect.data.repository.impl;

import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.data.dao.IStarDao;
import com.connect.data.entity.Star;
import com.connect.data.repository.IStarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class StarRepositoryImpl implements IStarRepository {

    @Autowired
    IStarDao istarDao;

    public StarRepositoryImpl(IStarDao istarDao) {
        this.istarDao = istarDao;
    }

    public void createStar(Star star) {
        if (istarDao.starExisting(star.getUserId(), star.getTargetId(), star.getTargetType())) {
            throw new ConnectDataException(ConnectErrorCode.STAR_EXISTED_EXCEPTION);
        }

        int affected = istarDao.createStar(star);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.STAR_CREATE_EXCEPTION);
        }
    }

    public void updateStar(Star star) {
        if (!istarDao.starExisting(star.getUserId(), star.getTargetId(), star.getTargetType())) {
            throw new ConnectDataException(ConnectErrorCode.STAR_NOT_EXISTED_EXCEPTION);
        }

        int affected = istarDao.updateStar(star);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.STAR_UPDATE_EXCEPTION);
        }
    }

    public boolean starExisting(String userId, Long targetId, Integer targetType) {
        return istarDao.starExisting(userId, targetId, targetType);
    }

    public boolean starExisting(String userId, Long targetId, Integer targetType, Boolean isActive) {
        return istarDao.starExistingWithTargetStatus(userId, targetId, targetType, isActive);
    }
}
