package com.connect.api.post.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class QueryPostVo {
    private Long id;

    private String username;

    private Integer status;

    private String content;

    private Long referenceId;

    private String tags;

    private Integer stars;

    private Integer views;

    private Date dbModifyTime;

    private QueryPostVo referencePost;
}
