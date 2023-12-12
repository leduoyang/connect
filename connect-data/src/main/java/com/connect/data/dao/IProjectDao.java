package com.connect.data.dao;

import com.connect.data.entity.Project;

import java.util.List;

public interface IProjectDao {
    int createProject(Project project);

    int updateProject(Project project);

    int deleteProject(Long id, String userId);

    Project queryProjectById(Long id);

    List<Project> queryProject(Long projectId, String userId, String keyword, String tags);

    boolean projectExisting(Long id, String userId);
}
