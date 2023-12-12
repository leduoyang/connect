package com.connect.data.repository;

import com.connect.data.entity.Project;
import com.connect.data.param.QueryProjectParam;

import java.util.List;

public interface IProjectRepository {
    void createProject(Project project);

    void deleteProject(Project project);

    List<Project> queryProject(QueryProjectParam param);

    Project queryProjectById(long id);

    void updateProject(Project project);
}
