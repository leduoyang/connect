package com.connect.api.user.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class QueryUserRequest {
    private String keyword;

    @Min(value = 1, message = "pageIndex must be at least 1")
    @Max(value = 2000, message = "pageIndex must be at most 200")
    private Integer pageIndex = 1;

    @Min(value = 10, message = "pageSize must be at least 10")
    @Max(value = 200, message = "pageSize must be at most 200")
    private Integer pageSize = 20;
}
