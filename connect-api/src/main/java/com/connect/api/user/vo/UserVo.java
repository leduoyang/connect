package com.connect.api.user.vo;

import com.connect.api.experience.vo.ExperienceVo;
import com.connect.api.socialLink.vo.SocialLinkVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Accessors(chain = true)
@Data
public class UserVo {
    private String username;

    private String description;

    private String profileImage;

    private Integer views;

    private Integer followers;

    private Integer followings;

    private List<SocialLinkVo> socialLinkVoList;

    private List<ExperienceVo> experienceVoList;
}
