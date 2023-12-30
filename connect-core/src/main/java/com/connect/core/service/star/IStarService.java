package com.connect.core.service.star;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.star.dto.StarDto;
import com.connect.api.star.dto.UnStarDto;
import com.connect.api.star.request.StarRequest;
import com.connect.api.star.request.UnStarRequest;
import com.connect.common.enums.StarTargetType;

import java.util.List;

public interface IStarService {
    void star(StarRequest request, RequestMetaInfo requestMetaInfo);

    void unStar(UnStarRequest request, RequestMetaInfo requestMetaInfo);

    boolean starExisting(long targetId, int targetType, RequestMetaInfo requestMetaInfo);
}
