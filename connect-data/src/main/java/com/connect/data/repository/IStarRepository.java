package com.connect.data.repository;

import com.connect.data.entity.Star;

public interface IStarRepository {
    void createStar(Star star);

    void updateStar(Star star);

    boolean starExisting(String userId, Long targetId, Integer targetType);

    boolean starExisting(String userId, Long targetId, Integer targetType, Boolean isActive);
}
