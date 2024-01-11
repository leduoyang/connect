package com.connect.api.socialLink.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class SocialLinkVo {
    private String platform;

    private String platformId;
}
