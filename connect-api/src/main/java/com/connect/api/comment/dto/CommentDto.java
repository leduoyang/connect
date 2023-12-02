package com.connect.api.comment.dto;

import com.connect.api.post.dto.PostDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Accessors(chain = true)
@Data
public class CommentDto {
    private Long id;

    private Long postId;

    private String content;

    private String createdUser;

    private String updatedUser;

    private Date dbModifyTime;

    private PostDto referencePost;
}
