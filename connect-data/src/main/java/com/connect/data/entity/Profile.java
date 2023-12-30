package com.connect.data.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class Profile {
    private Long userId;

    private String username;

    private String description;

    private String profileImage;

    private Date dbCreateTime;

    private Date dbModifyTime;
}
