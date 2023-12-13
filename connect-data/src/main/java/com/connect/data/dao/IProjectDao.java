package com.connect.data.dao;

import com.connect.data.entity.Project;

import java.util.List;

public interface IProjectDao {
    int createProject(Project project);

    int updateProject(Project project);

    int incrementViewCount(long id, int version);

    int deleteProject(long id, String userId);

    Project queryProjectById(long id);

    List<Project> queryProject(long projectId, String userId, String keyword, String tags);

    boolean projectExisting(long id, String userId);
}
