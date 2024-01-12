package com.connect.data.repository;

import com.connect.data.dto.SocialLinkDto;
import com.connect.data.entity.SocialLink;

import java.util.List;

public interface ISocialLinkRepository {
    void updateSocialLink(SocialLink socialLink);

    List<SocialLinkDto> internalQuerySocialLinkByUserId(long userId);
}
