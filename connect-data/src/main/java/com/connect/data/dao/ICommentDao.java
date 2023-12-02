package com.connect.data.dao;

import com.connect.data.entity.Comment;

import java.util.List;

public interface ICommentDao {
    Comment queryCommentById(Long id);

    List<Comment> queryComment(Long postId, String userId, String keyword);

    int createComment(Comment comment);

    int updateComment(Comment comment);

    int deleteComment(Long id);

    boolean commentExisting(Long id);
}
