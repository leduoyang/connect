package com.connect.web.controller.experience;

import com.connect.api.common.APIResponse;
import com.connect.api.common.RequestMetaInfo;
import com.connect.api.experience.IExperienceApi;
import com.connect.api.experience.request.CreateExperienceRequest;
import com.connect.api.experience.request.UpdateExperienceRequest;
import com.connect.core.service.experience.IExperienceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Validated
public class ExperienceController implements IExperienceApi {
    private final IExperienceService experienceService;

    public ExperienceController(IExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @Override
    public APIResponse<Long> createExperience(
            String authorizationHeader,
            CreateExperienceRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        Long id = experienceService.createExperience(request, requestMetaInfo);
        return APIResponse.getOKJsonResult(id);
    }

    @Override
    public APIResponse<Void> updateExperience(
            String authorizationHeader,
            Long experienceId,
            UpdateExperienceRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        experienceService.updateExperience(experienceId, request, requestMetaInfo);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> deleteExperience(
            String authorizationHeader,
            Long experienceId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        experienceService.deleteExperience(experienceId, requestMetaInfo);
        return APIResponse.getOKJsonResult(null);
    }
}
