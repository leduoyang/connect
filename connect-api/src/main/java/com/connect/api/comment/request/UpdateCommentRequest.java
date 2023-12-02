package com.connect.api.comment.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Accessors(chain = true)
@Data
public class UpdateCommentRequest {
    @Size(max = 3)
    private int status;

    @NotBlank
    @Size(min = 1, max = 500)
    private String content;

    @NotNull
    private String updatedUser;
}
