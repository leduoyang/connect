package com.connect.api.comment.response;

import com.connect.api.comment.vo.QueryCommentVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class QueryCommentResponse {
    private List<QueryCommentVo> items;

    private Integer total = 0;
}
