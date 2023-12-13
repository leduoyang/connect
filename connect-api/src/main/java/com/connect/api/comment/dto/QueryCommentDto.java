package com.connect.api.comment.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class QueryCommentDto {
    private Long id;

    private Integer status;

    private Long postId;

    private String content;

    private Integer likesCount;

    private Integer viewsCount;

    private String createdUser;

    private String updatedUser;

    private Date dbCreateTime;

    private Date dbModifyTime;
}
