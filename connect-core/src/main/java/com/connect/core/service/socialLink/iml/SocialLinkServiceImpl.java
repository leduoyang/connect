package com.connect.core.service.socialLink.iml;

import com.connect.api.common.RequestMetaInfo;
import com.connect.common.enums.SocialLinkMap;
import com.connect.core.service.socialLink.ISocialLinkService;
import com.connect.data.entity.SocialLink;
import com.connect.data.repository.ISocialLinkRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

@Log4j2
@Service
public class SocialLinkServiceImpl implements ISocialLinkService {
    private ISocialLinkRepository socialLinkRepository;

    public SocialLinkServiceImpl(
            ISocialLinkRepository socialLinkRepository
    ) {
        this.socialLinkRepository = socialLinkRepository;
    }

    @Override
    public void updateUserSocialLink(Map<String, String> socialLinkMap, RequestMetaInfo requestMetaInfo) {
        Arrays.stream(SocialLinkMap.values()).forEach(x -> {
            SocialLink socialLink = new SocialLink()
                    .setUserId(requestMetaInfo.getUserId())
                    .setPlatform(x.getKey())
                    .setPlatformId(socialLinkMap.get(x.getKey()));
            socialLinkRepository.updateSocialLink(socialLink);
        });
    }
}
