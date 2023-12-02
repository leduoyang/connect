package com.connect.data.repository;

import com.connect.data.entity.Comment;
import com.connect.data.param.QueryCommentParam;

import java.util.List;

public interface ICommentRepository {
    void createComment(Comment post);

    void deleteComment(Long id);

    List<Comment> queryComment(QueryCommentParam param);

    Comment queryCommentById(long id);

    void updateComment(Comment post);
}
