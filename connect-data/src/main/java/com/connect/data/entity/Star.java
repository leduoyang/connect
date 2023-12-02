package com.connect.data.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class Star {
    private Long id;

    private String username;

    private Long targetId;

    private int type;

    private Date dbCreateTime;
}
