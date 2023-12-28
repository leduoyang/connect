package com.connect.api.user.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UserVo {
    private String username;

    private String description;

    private Integer status;

    private String profileImage;

    private Integer views;

    private Integer followers;

    private Integer followings;
}
