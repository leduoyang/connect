package com.connect.api.post.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class QueryPostDto {
    private Long id;

    private Integer status;

    private String content;

    private Long referenceId;

    private Integer likesCount;

    private Integer viewsCount;

    private String createdUser;

    private String updatedUser;

    private Date dbCreateTime;

    private Date dbModifyTime;

    private QueryPostDto referencePost;
}
