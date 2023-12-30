package com.connect.api.comment.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class QueryCommentRequest {
    private String username;

    private String keyword;

    private String tags;

    @Min(value = 1, message = "pageIndex must be at least 1")
    @Max(value = 2000, message = "pageIndex must be at most 200")
    private Integer pageIndex = 1;

    @Min(value = 10, message = "pageSize must be at least 10")
    @Max(value = 200, message = "pageSize must be at most 200")
    private Integer pageSize = 20;
}
