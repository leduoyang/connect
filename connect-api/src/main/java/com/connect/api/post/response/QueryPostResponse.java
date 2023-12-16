package com.connect.api.post.response;

import com.connect.api.post.dto.QueryPostDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class QueryPostResponse {
    private List<QueryPostDto> items;

    private Integer total = 0;
}
