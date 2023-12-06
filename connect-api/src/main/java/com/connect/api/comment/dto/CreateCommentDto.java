package com.connect.api.comment.dto;

import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class CreateCommentDto {
    private int status;

    private Long postId;

    private String content;

    private String createdUser;
}
