package com.connect.core.service.project;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.project.dto.*;
import com.connect.api.project.request.QueryProjectRequest;

import java.util.List;

public interface IProjectService {
    QueryProjectResponseDto queryProjectById(long id, RequestMetaInfo requestMetaInfo);

    List<QueryProjectResponseDto> queryProject(QueryProjectRequest request, RequestMetaInfo requestMetaInfo);

    void createProject(CreateProjectDto request);

    void updateProject(UpdateProjectDto request);

    void deleteProject(DeleteProjectDto request);
}
