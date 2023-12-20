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

    long createComment(CreateCommentDto request, RequestMetaInfo requestMetaInfo);

    void updateComment(UpdateCommentDto request, RequestMetaInfo requestMetaInfo);

    void deleteComment(DeleteCommentDto id, RequestMetaInfo requestMetaInfo);
}
