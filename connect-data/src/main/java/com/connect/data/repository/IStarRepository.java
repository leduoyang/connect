package com.connect.data.repository;

import com.connect.data.entity.Star;

import java.util.List;

public interface IStarRepository {
    void createStar(Star star);

    void updateStar(Star star);

    boolean starExisting(String userId, long targetId, int targetType);

    boolean starExisting(String userId, long targetId, int targetType, Boolean isActive);

    int countStars(long targetId, int targetType);

    List<Integer> queryTargetIdList(int targetType, String userId);
}
