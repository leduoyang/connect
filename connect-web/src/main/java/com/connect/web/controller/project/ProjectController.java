package com.connect.web.controller.project;

import com.connect.api.common.APIResponse;
import com.connect.api.project.IProjectApi;
import com.connect.api.project.dto.CreateProjectDto;
import com.connect.api.project.dto.DeleteProjectDto;
import com.connect.api.project.dto.QueryProjectDto;
import com.connect.api.project.dto.UpdateProjectDto;
import com.connect.api.project.request.CreateProjectRequest;
import com.connect.api.project.request.QueryProjectRequest;
import com.connect.api.project.request.UpdateProjectRequest;
import com.connect.api.project.response.QueryProjectResponse;
import com.connect.common.enums.FollowStatus;
import com.connect.common.enums.ProjectStatus;
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
        QueryProjectDto projectDto = projectService.queryProjectById(projectId);
        if (projectDto.getStatus() == ProjectStatus.PRIVATE.getCode()
                && !projectDto.getCreatedUser().equals(authentication.getName())
        ) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                    "private post can only be viewed by creator"
            );
        }
        if (projectDto.getStatus() == ProjectStatus.SEMI.getCode() &&
                !followService.isFollowing(authentication.getName(), projectDto.getCreatedUser(), FollowStatus.APPROVED)
        ) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                    String.format("you have to follow user %s first to view his contents", projectDto.getCreatedUser())
            );
        }

        List<QueryProjectDto> projectDtoList = new ArrayList<>();
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
        List<QueryProjectDto> projectDtoList = projectService.queryProject(request);

        QueryProjectResponse response = new QueryProjectResponse()
                .setItems(projectDtoList)
                .setTotal(projectDtoList.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<Void> createProject(
            @RequestBody CreateProjectRequest request
    ) {
        CreateProjectDto createProjectDto = new CreateProjectDto();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(request, createProjectDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        createProjectDto.setCreatedUser(authentication.getName());

        projectService.createProject(createProjectDto);

        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> updateProject(
            @PathVariable Long projectId,
            @RequestBody UpdateProjectRequest request
    ) {
        UpdateProjectDto updateProjectDto = new UpdateProjectDto();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(request, updateProjectDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        updateProjectDto.setUpdatedUser(authentication.getName());
        updateProjectDto.setId(projectId);

        projectService.updateProject(updateProjectDto);

        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> deleteProject(
            @PathVariable Long projectId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        DeleteProjectDto deleteProjectDto = new DeleteProjectDto()
                .setId(projectId)
                .setUpdatedUser(authentication.getName());
        projectService.deleteProject(deleteProjectDto);

        return APIResponse.getOKJsonResult(null);
    }
}
