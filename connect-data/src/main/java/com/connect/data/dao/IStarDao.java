package com.connect.data.dao;

import com.connect.data.entity.Star;

import java.util.List;

public interface IStarDao {
    int createStar(Star star);

    int updateStar(Star star);

    boolean starExisting(long userId, long targetId, int targetType);

    boolean starExistingWithTargetStatus(long userId, long targetId, int targetType, Boolean isActive);

    int countStars(long targetId, int targetType);

    List<Integer> queryTargetIdList(int targetType, long userId);
}
