package com.connect.api.comment.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class DeleteCommentDto {
    private Long id;

    private String updatedUser;
}
