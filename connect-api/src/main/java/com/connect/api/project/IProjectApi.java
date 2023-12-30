package com.connect.api.project;

import com.connect.api.common.APIResponse;
import com.connect.api.project.request.CreateProjectRequest;
import com.connect.api.project.request.QueryProjectRequest;
import com.connect.api.project.request.UpdateProjectRequest;
import com.connect.api.project.response.QueryProjectResponse;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Hidden
@RequestMapping(value = "/api/connect/v1")
public interface IProjectApi {

    @GetMapping(value = "/project/{projectId}")
    APIResponse<QueryProjectResponse> queryProject(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @NotNull @PathVariable Long projectId
    );

    @GetMapping(value = "/project")
    APIResponse<QueryProjectResponse> queryProjectWithFilter(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated QueryProjectRequest request
    );

    @PostMapping(value = "/project")
    APIResponse<Long> createProject(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated @RequestBody CreateProjectRequest request
    );

    @PatchMapping(value = "/project/{projectId}")
    APIResponse<Void> updateProject(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated @NotNull @PathVariable Long projectId,
            @Validated @RequestBody UpdateProjectRequest request
    );

    @DeleteMapping(value = "/project/{projectId}")
    APIResponse<Void> deleteProject(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated @NotNull @PathVariable Long projectId
    );
}
