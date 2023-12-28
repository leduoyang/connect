package com.connect.core.service.comment;

import com.connect.api.comment.request.CreateCommentRequest;
import com.connect.api.comment.request.QueryCommentRequest;
import com.connect.api.comment.request.UpdateCommentRequest;
import com.connect.api.comment.vo.QueryCommentVo;
import com.connect.api.common.RequestMetaInfo;

import java.util.List;

public interface ICommentService {
    QueryCommentVo queryCommentById(long id, RequestMetaInfo requestMetaInfo);

    List<QueryCommentVo> queryComment(QueryCommentRequest request, RequestMetaInfo requestMetaInfo);

    long createComment(CreateCommentRequest request, RequestMetaInfo requestMetaInfo);

    void updateComment(long id, UpdateCommentRequest request, RequestMetaInfo requestMetaInfo);

    void deleteComment(long id, RequestMetaInfo requestMetaInfo);
}
