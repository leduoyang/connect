package com.connect.web.controller.project;

import com.connect.api.common.APIResponse;
import com.connect.api.common.RequestMetaInfo;
import com.connect.api.project.IProjectApi;
import com.connect.api.project.dto.*;
import com.connect.api.project.request.CreateProjectRequest;
import com.connect.api.project.request.QueryProjectRequest;
import com.connect.api.project.request.UpdateProjectRequest;
import com.connect.api.project.response.QueryProjectResponse;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.follow.IFollowService;
import com.connect.core.service.project.IProjectService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
                .setUserId(authentication.getName())
                .setDetails(authentication.getDetails());

        QueryProjectResponseDto projectDto = projectService.queryProjectById(projectId, requestMetaInfo);
        if (projectDto == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                    "target project not found or not authorized to retrieve"
            );
        }

        List<QueryProjectResponseDto> projectDtoList = new ArrayList<>();
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
                .setUserId(authentication.getName())
                .setDetails(authentication.getDetails());
        List<QueryProjectResponseDto> projectDtoList = projectService.queryProject(request, requestMetaInfo);

        QueryProjectResponse response = new QueryProjectResponse()
                .setItems(projectDtoList)
                .setTotal(projectDtoList.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<Long> createProject(
            @RequestBody CreateProjectRequest request
    ) {
        CreateProjectDto createProjectDto = new CreateProjectDto();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(request, createProjectDto);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(authentication.getName())
                .setDetails(authentication.getDetails());

        Long id = projectService.createProject(createProjectDto, requestMetaInfo);

        return APIResponse.getOKJsonResult(id);
    }

    @Override
    public APIResponse<Void> updateProject(
            @PathVariable Long projectId,
            @RequestBody UpdateProjectRequest request
    ) {
        UpdateProjectDto updateProjectDto = new UpdateProjectDto();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(request, updateProjectDto);
        updateProjectDto.setId(projectId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(authentication.getName())
                .setDetails(authentication.getDetails());

        projectService.updateProject(updateProjectDto, requestMetaInfo);

        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> deleteProject(
            @PathVariable Long projectId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(authentication.getName())
                .setDetails(authentication.getDetails());

        DeleteProjectDto deleteProjectDto = new DeleteProjectDto()
                .setId(projectId);

        projectService.deleteProject(deleteProjectDto, requestMetaInfo);

        return APIResponse.getOKJsonResult(null);
    }
}
