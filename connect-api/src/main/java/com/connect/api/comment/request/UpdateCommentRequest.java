package com.connect.api.comment.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UpdateCommentRequest {
    @Min(value = 0, message = "status must be at least 1 (0 - public, 1 - semi, 2 - private)")
    @Max(value = 2, message = "status must be at most 2 (0 - public, 1 - semi, 2 - private)")
    private Integer status;

    @NotNull
    @Min(value = 1, message = "comment must be at least 1")
    @Max(value = 500, message = "comment must be at most 500")
    private String content;
}
