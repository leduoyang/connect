package com.connect.core.service.comment;

import com.connect.api.comment.dto.CreateCommentDto;
import com.connect.api.comment.dto.DeleteCommentDto;
import com.connect.api.comment.dto.QueryCommentDto;
import com.connect.api.comment.dto.UpdateCommentDto;
import com.connect.api.comment.request.CreateCommentRequest;
import com.connect.api.comment.request.QueryCommentRequest;
import com.connect.api.comment.request.UpdateCommentRequest;

import java.util.List;

public interface ICommentService {
    QueryCommentDto queryCommentById(long id);

    List<QueryCommentDto> queryComment(QueryCommentRequest request);

    void createComment(CreateCommentDto request);

    void updateComment(UpdateCommentDto request);

    void deleteComment(DeleteCommentDto id);
}
