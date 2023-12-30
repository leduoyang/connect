package com.connect.data.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class CommentDto {
    private Long id;

    private Long postId;

    private String username;

    private String content;

    private Integer status;

    private Integer version;

    private String tags;

    private Integer stars;

    private Integer views;

    private Date dbModifyTime;
}