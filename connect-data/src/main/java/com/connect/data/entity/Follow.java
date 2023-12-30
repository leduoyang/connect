package com.connect.data.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class Follow {
    private Long id;

    private Long followingId;

    private Long followerId;

    private Integer status;

    private Date dbCreateTime;

    private Date dbModifyTime;
}
