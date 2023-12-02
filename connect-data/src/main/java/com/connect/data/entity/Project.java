package com.connect.data.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class Project {
    private Long id;

    private String title;

    private String description;

    private String tags;

    private int likesCount;

    private int viewsCount;

    private int version;

    private String createdUser;

    private String updatedUser;

    private Date dbCreateTime;

    private Date dbModifyTime;
}