package com.connect.data.repository.impl;

import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.data.dao.IProjectDao;
import com.connect.data.entity.Project;
import com.connect.data.param.QueryProjectParam;
import com.connect.data.repository.IProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class ProjectRepositoryImpl implements IProjectRepository {

    @Autowired
    IProjectDao projectDao;

    public ProjectRepositoryImpl(IProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public Project queryProjectById(long id, String userId) {
        return projectDao.queryProjectById(id, userId);
    }

    public List<Project> queryProject(QueryProjectParam param, String userId) {
        return projectDao.queryProject(
                param.getProjectId(),
                param.getUserId(),
                param.getKeyword(),
                param.getTags(),
                userId
        );
    }

    public void createProject(Project project) {
        int affected = projectDao.createProject(project);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.PROJECT_CREATE_EXCEPTION);
        }
    }

    public void updateProject(Project project) {
        long targetId = project.getId();
        String userId = project.getUpdatedUser();
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
            throw new ConnectDataException(ConnectErrorCode.PROJECT_UPDATE_EXCEPTION, "update viewCounts failed");
        }
    }

    public void refreshStars(long id, int version, int stars) {
        int affected = projectDao.refreshStars(id, version, stars);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.PROJECT_UPDATE_EXCEPTION, "update likesCount failed");
        }
    }

    public void deleteProject(Project project) {
        long targetId = project.getId();
        String userId = project.getUpdatedUser();
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
