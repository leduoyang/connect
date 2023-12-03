package com.connect.data.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class Profile {
    private Long id;

    private String userId;

    private int status;

    private String description;

    private String profileImage;

    private Date dbCreateTime;

    private Date dbModifyTime;
}
