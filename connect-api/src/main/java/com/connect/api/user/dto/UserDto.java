package com.connect.api.user.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class UserDto {
    private String userId;

    private String description;

    private Integer status;

    private Integer role;

    private String profileImage;

    private Integer likesCount;

    private Integer viewsCount;

    private Date dbCreateTime;

    private Date dbModifyTime;
}
