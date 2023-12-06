package com.connect.api.post.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;


@Accessors(chain = true)
@Data
public class UpdatePostRequest {
    @Min(value = 1, message = "status must be at least 1")
    @Max(value = 3, message = "status must be at most 3")
    private Integer status;

    @Size(max = 2000, message = "post must be at most 2000")
    private String content;

    private Long referenceId;
}
