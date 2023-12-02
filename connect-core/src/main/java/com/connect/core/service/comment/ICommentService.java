package com.connect.core.service.comment;

import com.connect.api.comment.dto.CommentDto;
import com.connect.api.comment.request.CreateCommentRequest;
import com.connect.api.comment.request.QueryCommentRequest;
import com.connect.api.comment.request.UpdateCommentRequest;

import java.util.List;

public interface ICommentService {
    CommentDto queryCommentById(long id);

    List<CommentDto> queryComment(QueryCommentRequest request);

    void createComment(CreateCommentRequest request);

    void updateComment(Long commentId, UpdateCommentRequest request);

    void deleteComment(Long id);
}
