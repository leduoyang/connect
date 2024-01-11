package com.connect.data.repository;

import com.connect.data.dto.ExperienceDto;
import com.connect.data.entity.Experience;

import java.util.List;

public interface IExperienceRepository {
    long createExperience(Experience experience);

    void updateExperience(Experience experience);

    void deleteExperience(Experience experience);

    List<ExperienceDto> internalQueryExperienceByUserId(long userId);
}
