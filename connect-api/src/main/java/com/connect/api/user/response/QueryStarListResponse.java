package com.connect.api.user.response;

import com.connect.api.comment.dto.QueryCommentResponseDto;
import com.connect.api.post.dto.QueryPostResponseDto;
import com.connect.api.project.dto.QueryProjectResponseDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class QueryStarListResponse {
    private List<QueryPostResponseDto> posts;

    private List<QueryProjectResponseDto> projects;

    private List<QueryCommentResponseDto> comments;

    private int totalPosts = 0;

    private int totalProjects = 0;

    private int totalComments = 0;
}
