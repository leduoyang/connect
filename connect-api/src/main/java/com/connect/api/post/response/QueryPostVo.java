package com.connect.api.post.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class QueryPostVo {
    private List<com.connect.api.post.vo.QueryPostVo> items;

    private Integer total = 0;
}
