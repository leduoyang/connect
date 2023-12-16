package com.connect.api.post.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class UpdatePostRequest {
    @Min(value = 0, message = "status must be at least 0 (0 - public, 1 - semi, 2 - private)")
    @Max(value = 2, message = "status must be at most 2 (0 - public, 1 - semi, 2 - private)")
    private Integer status;

    @Size(min = 1, max = 2000, message = "post must be at most 2000")
    private String content;

    private Long referenceId;

    @Size(max = 200, message = "tags must be at most 200")
    private String tags;
}
