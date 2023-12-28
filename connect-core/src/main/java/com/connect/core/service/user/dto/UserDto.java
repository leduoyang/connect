package com.connect.core.service.user.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UserDto {
    private Long userId;

    private String uuid;

    private String username;

    private String description;

    private Integer status;

    private Integer role;

    private String phone;

    private String email;

    private String profileImage;

    private Integer views;

    private Integer followers;

    private Integer followings;
}
