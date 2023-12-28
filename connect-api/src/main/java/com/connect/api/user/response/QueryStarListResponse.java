package com.connect.api.user.response;

import com.connect.api.comment.vo.QueryCommentVo;
import com.connect.api.post.vo.QueryPostVo;
import com.connect.api.project.vo.QueryProjectVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class QueryStarListResponse {
    private List<QueryPostVo> posts;

    private List<QueryProjectVo> projects;

    private List<QueryCommentVo> comments;

    private int totalPosts = 0;

    private int totalProjects = 0;

    private int totalComments = 0;
}
