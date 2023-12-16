package com.connect.data.param;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class QueryProjectParam {
    private Long projectId;

    private String userId;

    private String keyword;

    private String tags;
}
