package com.connect.api.experience;

import com.connect.api.common.APIResponse;
import com.connect.api.experience.request.CreateExperienceRequest;
import com.connect.api.experience.request.UpdateExperienceRequest;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Hidden
@RequestMapping(value = "/api/connect/v1")
public interface IExperienceApi {
    @PostMapping(value = "/experience")
    APIResponse<Long> createExperience(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated @RequestBody CreateExperienceRequest request
    );

    @PatchMapping(value = "/experience/{experienceId}")
    APIResponse<Void> updateExperience(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated @NotNull @PathVariable Long experienceId,
            @Validated @RequestBody UpdateExperienceRequest request
    );

    @DeleteMapping(value = "/experience/{experienceId}")
    APIResponse<Void> deleteExperience(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated @NotNull @PathVariable Long experienceId
    );
}
