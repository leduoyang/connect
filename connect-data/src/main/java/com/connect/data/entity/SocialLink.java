package com.connect.data.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class SocialLink {
    private Long id;

    private String platform;

    private String platformId;

    private Long userId;

    private Date dbCreateTime;

    private Date dbModifyTime;
}
