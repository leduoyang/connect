package com.connect.data.repository;

import com.connect.data.entity.Project;
import com.connect.data.param.QueryProjectParam;

import java.util.List;

public interface IProjectRepository {
    void createProject(Project project);

    void updateProject(Project project);

    void incrementViews(long id, int version);

    void refreshStars(long id, int version, int stars);

    void deleteProject(Project project);

    Project queryProjectById(long id);

    List<Project> queryProject(QueryProjectParam param);
}
