package com.connect.core.service.project;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.project.dto.*;
import com.connect.api.project.request.QueryProjectRequest;

import java.util.List;

public interface IProjectService {
    QueryProjectResponseDto queryProjectById(long id, RequestMetaInfo requestMetaInfo);

    List<QueryProjectResponseDto> queryProject(QueryProjectRequest request, RequestMetaInfo requestMetaInfo);

    long createProject(CreateProjectDto request, RequestMetaInfo requestMetaInfo);

    void updateProject(UpdateProjectDto request, RequestMetaInfo requestMetaInfo);

    void deleteProject(DeleteProjectDto request, RequestMetaInfo requestMetaInfo);
}
