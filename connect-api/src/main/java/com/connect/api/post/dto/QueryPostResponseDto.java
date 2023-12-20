package com.connect.api.post.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class QueryPostResponseDto {
    private Long id;

    private Integer status;

    private String content;

    private Long referenceId;

    private String tags;

    private Integer stars;

    private Integer views;

    private String createdUser;

    private String updatedUser;

    private Date dbCreateTime;

    private Date dbModifyTime;

    private QueryPostResponseDto referencePost;
}
