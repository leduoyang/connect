package com.connect.core.service.socialLink;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.socialLink.vo.SocialLinkVo;
import com.connect.data.dto.SocialLinkDto;

import java.util.List;
import java.util.Map;

public interface ISocialLinkService {
    void updateUserSocialLink(Map<String, String> socialLinkDto, RequestMetaInfo requestMetaInfo);

    List<SocialLinkDto> internalQuerySocialLinkByUserId(long userId);
}
