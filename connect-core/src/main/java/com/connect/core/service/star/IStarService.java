package com.connect.core.service.star;

import com.connect.api.star.dto.StarDto;
import com.connect.api.star.dto.UnStarDto;
import com.connect.common.enums.StarTargetType;

public interface IStarService {
    void star(StarDto request);

    void unStar(UnStarDto request);

    boolean starExisting(String userId, long targetId, int targetType, Boolean isActive);
}
