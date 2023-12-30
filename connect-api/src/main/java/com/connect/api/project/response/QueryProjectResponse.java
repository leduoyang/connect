package com.connect.api.project.response;

import com.connect.api.project.vo.QueryProjectVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class QueryProjectResponse {
    private List<QueryProjectVo> items;

    private Integer total = 0;
}
