package com.connect.data.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class Comment {
    private Long id;

    private Long postId;

    private Integer status;

    private String content;

    private Integer stars;

    private Integer views;

    private Integer version;

    private Long createdUser;

    private Long updatedUser;

    private Date dbCreateTime;

    private Date dbModifyTime;
}