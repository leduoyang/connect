package com.connect.api.post.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class CreatePostRequest {
    @Min(value = 0, message = "status must be at least 1 (0 - public, 1 - semi, 2 - private)")
    @Max(value = 2, message = "status must be at most 2 (0 - public, 1 - semi, 2 - private)")
    private int status;

    private Long referenceId;

    @Size(min = 1, max = 2000, message = "post must be at most 2000")
    private String content;
}