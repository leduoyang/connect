package com.connect.api.post.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Accessors(chain = true)
@Data
public class CreatePostRequest {
    @NotNull
    private String userId;

    @Size(max = 3)
    private int status = 1;

    @NotBlank
    private Long referenceId;

    @NotBlank
    @Size(max = 2000)
    private String content;
}