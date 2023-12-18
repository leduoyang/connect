package com.connect.api.post.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class CreatePostDto {
    private int status;

    private String content;

    private Long referenceId;

    private String tags;

    private String createdUser;
}
