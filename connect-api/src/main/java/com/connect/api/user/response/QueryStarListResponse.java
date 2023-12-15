package com.connect.api.user.response;

import com.connect.api.comment.dto.QueryCommentDto;
import com.connect.api.post.dto.QueryPostDto;
import com.connect.api.project.dto.QueryProjectDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class QueryStarListResponse {
    private List<QueryPostDto> posts;

    private List<QueryProjectDto> projects;

    private List<QueryCommentDto> comments;

    private int totalPosts = 0;

    private int totalProjects = 0;

    private int totalComments = 0;
}
