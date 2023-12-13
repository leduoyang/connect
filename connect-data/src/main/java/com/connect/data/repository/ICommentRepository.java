package com.connect.data.repository;

import com.connect.data.entity.Comment;
import com.connect.data.param.QueryCommentParam;

import java.util.List;

public interface ICommentRepository {
    void createComment(Comment comment);

    void updateComment(Comment comment);

    void incrementViewCount(long id, int version);

    void deleteComment(Comment comment);

    Comment queryCommentById(long id);

    List<Comment> queryComment(QueryCommentParam param);
}
