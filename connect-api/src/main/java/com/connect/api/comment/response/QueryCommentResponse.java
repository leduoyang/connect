package com.connect.api.comment.response;

import com.connect.api.comment.dto.QueryCommentResponseDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class QueryCommentResponse {
    private List<QueryCommentResponseDto> items;

    private Integer total = 0;
}
