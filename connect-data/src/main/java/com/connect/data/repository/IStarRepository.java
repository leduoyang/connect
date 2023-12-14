package com.connect.data.repository;

import com.connect.data.entity.Star;

public interface IStarRepository {
    void createStar(Star star);

    void updateStar(Star star);

    boolean starExisting(String userId, long targetId, int targetType);

    boolean starExisting(String userId, long targetId, int targetType, Boolean isActive);

    int countStars(long targetId, int targetType);
}
