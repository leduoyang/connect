package com.connect.data.dao;

import com.connect.data.dto.ExperienceDto;
import com.connect.data.entity.Experience;

import java.util.List;

public interface IExperienceDao {
    int createExperience(Experience experience);

    int updateExperience(Experience experience);

    boolean experienceExisting(long id, long userId);

    int deleteExperience(long id, long userId);

    List<ExperienceDto> internalQueryExperienceByUserId(long userId);
}
