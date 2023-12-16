package com.connect.core.service.project;

import com.connect.api.project.dto.CreateProjectDto;
import com.connect.api.project.dto.DeleteProjectDto;
import com.connect.api.project.dto.QueryProjectDto;
import com.connect.api.project.dto.UpdateProjectDto;
import com.connect.api.project.request.QueryProjectRequest;

import java.util.List;

public interface IProjectService {
    QueryProjectDto queryProjectById(long id);

    List<QueryProjectDto> queryProject(QueryProjectRequest request);

    void createProject(CreateProjectDto request);

    void updateProject(UpdateProjectDto request);

    void deleteProject(DeleteProjectDto request);
}
