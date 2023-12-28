package com.connect.web.controller.project;

import com.connect.api.common.APIResponse;
import com.connect.api.common.RequestMetaInfo;
import com.connect.api.project.IProjectApi;
import com.connect.api.project.request.CreateProjectRequest;
import com.connect.api.project.request.QueryProjectRequest;
import com.connect.api.project.request.UpdateProjectRequest;
import com.connect.api.project.response.QueryProjectResponse;
import com.connect.api.project.vo.QueryProjectVo;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.follow.IFollowService;
import com.connect.core.service.project.IProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@Validated
public class ProjectController implements IProjectApi {
    private final IProjectService projectService;

    private IFollowService followService;

    public ProjectController(IProjectService projectService, IFollowService followService) {
        this.projectService = projectService;
        this.followService = followService;
    }

    @Override
    public APIResponse<QueryProjectResponse> queryProject(Long projectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        QueryProjectVo projectDto = projectService.queryProjectById(projectId, requestMetaInfo);
        if (projectDto == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                    "target project not found or not authorized to retrieve"
            );
        }

        List<QueryProjectVo> projectDtoList = new ArrayList<>();
        projectDtoList.add(projectDto);
        QueryProjectResponse response = new QueryProjectResponse()
                .setItems(projectDtoList)
                .setTotal(projectDtoList.size());

        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryProjectResponse> queryProjectWithFilter(
            QueryProjectRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());
        List<QueryProjectVo> projectDtoList = projectService.queryProject(request, requestMetaInfo);

        QueryProjectResponse response = new QueryProjectResponse()
                .setItems(projectDtoList)
                .setTotal(projectDtoList.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<Long> createProject(
            @RequestBody CreateProjectRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        Long id = projectService.createProject(request, requestMetaInfo);
        return APIResponse.getOKJsonResult(id);
    }

    @Override
    public APIResponse<Void> updateProject(
            @PathVariable Long projectId,
            @RequestBody UpdateProjectRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        projectService.updateProject(projectId, request, requestMetaInfo);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> deleteProject(
            @PathVariable Long projectId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        projectService.deleteProject(projectId, requestMetaInfo);
        return APIResponse.getOKJsonResult(null);
    }
}
