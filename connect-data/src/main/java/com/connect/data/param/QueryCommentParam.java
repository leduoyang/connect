package com.connect.data.param;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class QueryCommentParam {
    private String keyword;

    private String tags;

    private String username;
}
