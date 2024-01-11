package com.connect.core.service.experience;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.experience.request.CreateExperienceRequest;
import com.connect.api.experience.request.UpdateExperienceRequest;

public interface IExperienceService {
    long createExperience(CreateExperienceRequest request, RequestMetaInfo requestMetaInfo);

    void updateExperience(long id, UpdateExperienceRequest request, RequestMetaInfo requestMetaInfo);

    void deleteExperience(long id, RequestMetaInfo requestMetaInfo);
}
