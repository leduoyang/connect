package com.connect.data.repository.impl;

import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.data.dao.IProjectDao;
import com.connect.data.dto.ProjectDto;
import com.connect.data.entity.Project;
import com.connect.data.param.QueryProjectParam;
import com.connect.data.repository.IProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
public class ProjectRepositoryImpl implements IProjectRepository {

    @Autowired
    IProjectDao projectDao;

    public ProjectRepositoryImpl(IProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public ProjectDto queryProjectById(long id, long userId) {
        return projectDao.queryProjectById(id, userId);
    }

    public Project internalQueryProjectById(long id) {
        return projectDao.internalQueryProjectById(id);
    }

    public List<ProjectDto> queryProject(QueryProjectParam param, long userId) {
        return projectDao.queryProject(
                param.getKeyword(),
                param.getTags(),
                param.getUsername(),
                userId
        );
    }

    public long createProject(Project project) {
        project.setUuid(UUID.randomUUID().toString());
        int affected = projectDao.createProject(project);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.PROJECT_CREATE_EXCEPTION);
        }

        return project.getId();
    }

    public void updateProject(Project project) {
        long targetId = project.getId();
        Long userId = project.getUpdatedUser();
        boolean existed = projectDao.projectExisting(targetId, userId);
        if (!existed) {
            throw new ConnectDataException(
                    ConnectErrorCode.PROJECT_NOT_EXISTED_EXCEPTION,
                    String.format("Project %s not exited or user %s is not the creator", targetId, userId)
            );
        }

        int affected = projectDao.updateProject(project);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.PROJECT_UPDATE_EXCEPTION);
        }
    }

    public void incrementViews(long id, int version) {
        int affected = projectDao.incrementViews(id, version);
        if (affected <= 0) {
            log.error(ConnectErrorCode.OPTIMISTIC_LOCK_CONFLICT_EXCEPTION + "project incrementViews failed");
        }
    }

    public void refreshStars(long id, int version, int stars) {
        int affected = projectDao.refreshStars(id, version, stars);
        if (affected <= 0) {
            log.error(ConnectErrorCode.OPTIMISTIC_LOCK_CONFLICT_EXCEPTION + "project refreshStars failed");
        }
    }

    public void deleteProject(Project project) {
        long targetId = project.getId();
        Long userId = project.getUpdatedUser();
        boolean existed = projectDao.projectExisting(targetId, userId);
        if (!existed) {
            throw new ConnectDataException(
                    ConnectErrorCode.PROJECT_NOT_EXISTED_EXCEPTION,
                    String.format("Project %s not exited or user %s is not the creator", targetId, userId)
            );
        }

        int affected = projectDao.deleteProject(targetId, userId);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.PROJECT_UPDATE_EXCEPTION);
        }
    }
}
