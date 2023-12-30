package com.connect.api.comment.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class QueryCommentVo {
    private Long id;

    private Integer status;

    private Long postId;

    private String content;

    private Integer stars;

    private Integer views;

    private String username;

    private Date dbModifyTime;
}
