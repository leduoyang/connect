package com.connect.api.post.response;

import com.connect.api.post.dto.PostDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class QueryPostResponse {
    private List<PostDto> items;

    private int total = 0;
}
