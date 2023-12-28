package com.connect.data.dao;

import com.connect.data.entity.Project;

import java.util.List;

public interface IProjectDao {
    int createProject(Project project);

    int updateProject(Project project);

    int incrementViews(long id, int version);

    int refreshStars(long id, int version, int stars);

    int deleteProject(long id, long userId);

    Project queryProjectById(long id, long userId);

    Project internalQueryProjectById(long id);

    List<Project> queryProject(String keyword, String tags, long userId);

    boolean projectExisting(long id, long userId);
}
