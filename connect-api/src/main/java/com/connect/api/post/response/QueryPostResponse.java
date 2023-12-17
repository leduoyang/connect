package com.connect.api.post.response;

import com.connect.api.post.dto.QueryPostResponseDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class QueryPostResponse {
    private List<QueryPostResponseDto> items;

    private Integer total = 0;
}
