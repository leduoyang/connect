package com.connect.data.repository.impl;

import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.data.dao.IExperienceDao;
import com.connect.data.dao.ISocialLinkDao;
import com.connect.data.dto.ExperienceDto;
import com.connect.data.entity.Experience;
import com.connect.data.entity.SocialLink;
import com.connect.data.repository.IExperienceRepository;
import com.connect.data.repository.ISocialLinkRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class ExperienceRepositoryImpl implements IExperienceRepository {
    @Autowired
    IExperienceDao experienceDao;

    public ExperienceRepositoryImpl(IExperienceDao experienceDao) {
        this.experienceDao = experienceDao;
    }

    public List<ExperienceDto> internalQueryExperienceByUserId(long userId) {
        return experienceDao.internalQueryExperienceByUserId(userId);
    }

    public long createExperience(Experience experience) {
        int affected = experienceDao.createExperience(experience);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.EXPERIENCE_CREATE_EXCEPTION);
        }

        return experience.getId();
    }

    public void updateExperience(Experience experience) {
        long targetId = experience.getId();
        Long userId = experience.getUserId();
        boolean existed = experienceDao.experienceExisting(targetId, userId);
        if (!existed) {
            throw new ConnectDataException(
                    ConnectErrorCode.EXPERIENCE_NOT_EXISTED_EXCEPTION,
                    String.format("Experience %s not exited or user %s is not the creator", targetId, userId)
            );
        }

        int affected = experienceDao.updateExperience(experience);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.EXPERIENCE_UPDATE_EXCEPTION);
        }
    }

    public void deleteExperience(Experience experience) {
        long targetId = experience.getId();
        Long userId = experience.getUserId();
        boolean existed = experienceDao.experienceExisting(targetId, userId);
        if (!existed) {
            throw new ConnectDataException(
                    ConnectErrorCode.EXPERIENCE_NOT_EXISTED_EXCEPTION,
                    String.format("Experience %s not exited or user %s is not the creator", targetId, userId)
            );
        }

        int affected = experienceDao.deleteExperience(targetId, userId);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.EXPERIENCE_DELETE_EXCEPTION);
        }
    }
}
