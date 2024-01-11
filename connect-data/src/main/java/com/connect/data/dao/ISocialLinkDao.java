package com.connect.data.dao;

import com.connect.data.dto.SocialLinkDto;
import com.connect.data.entity.SocialLink;

import java.util.List;

public interface ISocialLinkDao {
    int createSocialLink(SocialLink socialLink);

    int updateSocialLink(SocialLink socialLink);

    boolean platformExisting(Long userId, String platform);

    boolean platformIdExisting(Long userId, String platform);

    List<SocialLinkDto> internalQuerySocialLinkByUserId(long userId);
}
