package com.connect.core.service.socialLink;

import com.connect.api.common.RequestMetaInfo;

import java.util.Map;

public interface ISocialLinkService {
    void updateUserSocialLink(Map<String, String> socialLinkDto, RequestMetaInfo requestMetaInfo);
}
