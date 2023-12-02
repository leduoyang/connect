package com.connect.api.comment.response;

import com.connect.api.comment.dto.CommentDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

@Accessors(chain = true)
@Data
public class QueryCommentResponse {
    private List<CommentDto> items;

    private int total = 0;
}
