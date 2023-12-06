package com.connect.api.comment.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class CreateCommentRequest {
    @NotNull( message = "postId can not be null when creating comments")
    private Long postId;

    @Min(value = 1, message = "status must be at least 1")
    @Max(value = 3, message = "status must be at most 3")
    private Integer status = 1;

    @NotNull
    @Size(min = 1, max = 500)
    private String content;
}
