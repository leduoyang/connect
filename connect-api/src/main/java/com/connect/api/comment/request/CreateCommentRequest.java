package com.connect.api.comment.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Accessors(chain = true)
@Data
public class CreateCommentRequest {
    @NotNull
    private Long postId;

    @NotNull
    private String userId;

    @Size(max = 3)
    private int status = 1;

    @NotBlank
    @Size(min = 1, max = 500)
    private String content;
}
