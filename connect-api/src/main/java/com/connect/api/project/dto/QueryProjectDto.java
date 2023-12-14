package com.connect.api.project.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class QueryProjectDto {
    private Long id;

    private String title;

    private String description;

    private Integer status;

    private String tags;

    private Integer boosted;

    private Integer stars;

    private Integer views;

    private String createdUser;

    private String updatedUser;

    private Date dbCreateTime;

    private Date dbModifyTime;
}
