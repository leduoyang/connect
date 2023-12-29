package com.connect.data.repository;

import com.connect.data.dto.ProjectDto;
import com.connect.data.entity.Project;
import com.connect.data.param.QueryProjectParam;

import java.util.List;

public interface IProjectRepository {
    long createProject(Project project);

    void updateProject(Project project);

    void incrementViews(long id, int version);

    void refreshStars(long id, int version, int stars);

    void deleteProject(Project project);

    ProjectDto queryProjectById(long id, long userId);

    Project internalQueryProjectById(long id);

    List<ProjectDto> queryProject(QueryProjectParam param, long userId);
}
