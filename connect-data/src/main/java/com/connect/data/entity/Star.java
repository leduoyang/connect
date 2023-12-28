package com.connect.data.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class Star {
    private Long id;

    private Long userId;

    private Long targetId;

    private Integer targetType;

    private Boolean isActive;

    private Date dbCreateTime;

    private Date dbModifyTime;
}
