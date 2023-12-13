package com.connect.data.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Version;

import java.util.Date;

@Accessors(chain = true)
@Data
public class Project {
    private Long id;

    private String title;

    private String description;

    private Integer status;

    private String tags;

    private Integer boosted;

    private Integer likesCount;

    private Integer viewsCount;

    @Version
    private Integer version;

    private String createdUser;

    private String updatedUser;

    private Date dbCreateTime;

    private Date dbModifyTime;
}