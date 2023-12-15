package com.connect.data.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class Follow {
    private Long id;

    private String followingId;

    private String followerId;

    private Boolean isActive;

    private Date dbCreateTime;

    private Date dbModifyTime;
}
