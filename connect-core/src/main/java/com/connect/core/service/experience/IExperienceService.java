package com.connect.core.service.experience;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.experience.request.CreateExperienceRequest;
import com.connect.api.experience.request.UpdateExperienceRequest;
import com.connect.data.dto.ExperienceDto;
import com.connect.data.entity.Experience;

import java.util.List;

public interface IExperienceService {
    long createExperience(CreateExperienceRequest request, RequestMetaInfo requestMetaInfo);

    void updateExperience(long id, UpdateExperienceRequest request, RequestMetaInfo requestMetaInfo);

    void deleteExperience(long id, RequestMetaInfo requestMetaInfo);

    List<ExperienceDto> internalQueryExperienceByUserId(long userId);
}
