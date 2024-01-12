package com.connect.data.repository.impl;

import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.data.dao.ISocialLinkDao;
import com.connect.data.dto.SocialLinkDto;
import com.connect.data.entity.SocialLink;
import com.connect.data.repository.ISocialLinkRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class SocialLinkRepositoryImpl implements ISocialLinkRepository {
    @Autowired
    ISocialLinkDao socialLinkDao;

    public SocialLinkRepositoryImpl(ISocialLinkDao socialLinkDao) {
        this.socialLinkDao = socialLinkDao;
    }

    public List<SocialLinkDto> internalQuerySocialLinkByUserId(long userId) {
        return socialLinkDao.internalQuerySocialLinkByUserId(userId);
    }

    public void updateSocialLink(SocialLink socialLink) {
        log.info("payload for updating social link - " + socialLink);
        if (!socialLinkDao.platformExisting(socialLink.getUserId(), socialLink.getPlatform())) {
            int affected = socialLinkDao.createSocialLink(socialLink);
            if (affected <= 0) {
                throw new ConnectDataException(ConnectErrorCode.SOCIAL_LINK_CREATE_EXCEPTION);
            }
        }

        int affected = socialLinkDao.updateSocialLink(socialLink);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.SOCIAL_LINK_UPDATE_EXCEPTION);
        }
    }
}
