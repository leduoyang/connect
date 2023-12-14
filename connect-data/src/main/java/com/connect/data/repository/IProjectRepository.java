package com.connect.data.repository;

import com.connect.data.entity.Project;
import com.connect.data.param.QueryProjectParam;

import java.util.List;

public interface IProjectRepository {
    void createProject(Project project);

    void updateProject(Project project);

    void incrementViewCount(long id, int version);

    void refreshLikeCount(long id, int version, int likesCount);

    void deleteProject(Project project);

    Project queryProjectById(long id);

    List<Project> queryProject(QueryProjectParam param);
}
