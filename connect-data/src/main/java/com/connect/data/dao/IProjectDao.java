package com.connect.data.dao;

import com.connect.data.dto.ProjectDto;
import com.connect.data.entity.Project;

import java.util.List;

public interface IProjectDao {
    int createProject(Project project);

    int updateProject(Project project);

    int incrementViews(long id, int version);

    int refreshStars(long id, int version, int stars);

    int deleteProject(long id, long userId);

    ProjectDto queryProjectById(long id, long userId);

    Project internalQueryProjectById(long id);

    List<ProjectDto> queryProject(String keyword, String tags, String username, long userId);

    boolean projectExisting(long id, long userId);
}
