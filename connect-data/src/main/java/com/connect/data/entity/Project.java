package com.connect.data.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class Project {
    private Long id;

    private String uuid;

    private String title;

    private String description;

    private Integer status;

    private String tags;

    private Integer boosted;

    private Integer stars;

    private Integer views;

    private Integer version;

    private Long createdUser;

    private Long updatedUser;

    private Date dbCreateTime;

    private Date dbModifyTime;
}