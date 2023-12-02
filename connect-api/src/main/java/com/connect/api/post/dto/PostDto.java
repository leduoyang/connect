package com.connect.api.post.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class PostDto {
    private Long id;

    private String content;

    private Long referenceId;

    private String updatedUser;

    private Date dbModifyTime;

    private PostDto referencePost;
}
