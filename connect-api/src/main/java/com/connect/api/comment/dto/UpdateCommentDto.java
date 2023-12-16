package com.connect.api.comment.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UpdateCommentDto {
    private Long id;

    private Integer status;

    private String content;

    private String updatedUser;
}
