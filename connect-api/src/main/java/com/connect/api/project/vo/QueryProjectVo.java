package com.connect.api.project.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class QueryProjectVo {
    private Long id;

    private String title;

    private String description;

    private Integer status;

    private String tags;

    private Integer boosted;

    private Integer stars;

    private Integer views;

    private Long createdUser;

    private Long updatedUser;

    private Date dbCreateTime;

    private Date dbModifyTime;
}
