package com.connect.data.repository;

import com.connect.data.entity.Comment;
import com.connect.data.param.QueryCommentParam;

import java.util.List;

public interface ICommentRepository {
    long createComment(Comment comment);

    void updateComment(Comment comment);

    void incrementViews(long id, int version);

    void refreshStars(long id, int version, int stars);

    void deleteComment(Comment comment);

    Comment queryCommentById(long id, String userId);

    List<Comment> queryComment(QueryCommentParam param, String userId);
}
