package com.connect.data.dao;

import com.connect.data.entity.SocialLink;

public interface ISocialLinkDao {
    int createSocialLink(SocialLink socialLink);

    int updateSocialLink(SocialLink socialLink);

    boolean platformExisting(Long userId, String platform);

    boolean platformIdExisting(Long userId, String platform);
}
