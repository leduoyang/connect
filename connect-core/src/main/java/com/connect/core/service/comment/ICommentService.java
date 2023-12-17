package com.connect.core.service.comment;

import com.connect.api.comment.dto.*;
import com.connect.api.comment.request.CreateCommentRequest;
import com.connect.api.comment.request.QueryCommentRequest;
import com.connect.api.comment.request.UpdateCommentRequest;
import com.connect.api.common.RequestMetaInfo;

import java.util.List;

public interface ICommentService {
    QueryCommentResponseDto queryCommentById(long id, RequestMetaInfo requestMetaInfo);

    List<QueryCommentResponseDto> queryComment(QueryCommentRequest request, RequestMetaInfo requestMetaInfo);

    void createComment(CreateCommentDto request);

    void updateComment(UpdateCommentDto request);

    void deleteComment(DeleteCommentDto id);
}
