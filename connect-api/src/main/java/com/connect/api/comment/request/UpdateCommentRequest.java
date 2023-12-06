package com.connect.api.comment.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UpdateCommentRequest {
    @Min(value = 1, message = "status must be at least 1")
    @Max(value = 3, message = "status must be at most 3")
    private Integer status;

    @NotNull
    @Min(value = 1, message = "comment must be at least 1")
    @Max(value = 500, message = "comment must be at most 500")
    private String content;
}
