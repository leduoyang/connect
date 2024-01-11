package com.connect.data.repository;

import com.connect.data.entity.Experience;

public interface IExperienceRepository {
    long createExperience(Experience experience);

    void updateExperience(Experience experience);

    void deleteExperience(Experience experience);
}
