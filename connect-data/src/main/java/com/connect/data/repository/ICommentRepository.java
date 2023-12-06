package com.connect.data.repository;

import com.connect.data.entity.Comment;
import com.connect.data.param.QueryCommentParam;

import java.util.List;

public interface ICommentRepository {
    void createComment(Comment comment);

    void deleteComment(Comment comment);

    List<Comment> queryComment(QueryCommentParam param);

    Comment queryCommentById(long id);

    void updateComment(Comment comment);
}
