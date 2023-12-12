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

    @Min(value = 0, message = "status must be at least 1 (0 - public, 1 - semi, 2 - private)")
    @Max(value = 2, message = "status must be at most 2 (0 - public, 1 - semi, 2 - private)")
    private int status;

    @NotNull
    @Size(min = 1, max = 500)
    private String content;

    @Size(max = 200, message = "tags must be at most 200")
    private String tags;
}
