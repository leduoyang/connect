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

    private String email;

    private String phone;

    private String profileImage;

    private Date dbModifyTime;
}
