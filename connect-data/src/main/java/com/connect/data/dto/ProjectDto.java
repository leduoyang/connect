package com.connect.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class ProjectDto {
    private Long id;

    private String username;

    private String title;

    private String description;

    private Integer status;

    private String tags;

    private Integer boosted;

    private Integer stars;

    private Integer views;

    private Integer version;

    private Date dbModifyTime;
}