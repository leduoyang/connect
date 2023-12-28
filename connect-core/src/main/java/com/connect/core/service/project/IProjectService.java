package com.connect.core.service.project;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.project.request.CreateProjectRequest;
import com.connect.api.project.request.QueryProjectRequest;
import com.connect.api.project.request.UpdateProjectRequest;
import com.connect.api.project.vo.QueryProjectVo;

import java.util.List;

public interface IProjectService {
    QueryProjectVo queryProjectById(long id, RequestMetaInfo requestMetaInfo);

    List<QueryProjectVo> queryProject(QueryProjectRequest request, RequestMetaInfo requestMetaInfo);

    long createProject(CreateProjectRequest request, RequestMetaInfo requestMetaInfo);

    void updateProject(long id, UpdateProjectRequest request, RequestMetaInfo requestMetaInfo);

    void deleteProject(long id, RequestMetaInfo requestMetaInfo);
}
