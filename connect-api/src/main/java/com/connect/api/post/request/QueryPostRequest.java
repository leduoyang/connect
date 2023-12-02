package com.connect.api.post.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@Data
public class QueryPostRequest {
    private Long postId;

    private String userId;

    private String keyword;

    @NotNull
    @Min(1)
    @Max(2000)
    private int pageIndex = 1;

    @NotNull
    @Min(1)
    @Max(200)
    private int pageSize = 20;
}
