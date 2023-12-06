package com.connect.data.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class Comment {
    private Long id;

    private Long postId;

    private int status;

    private String content;

    private String createdUser;

    private String updatedUser;

    private Date dbCreateTime;

    private Date dbModifyTime;
}