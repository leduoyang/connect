package com.connect.data.dao;

import com.connect.data.entity.Comment;

import java.util.List;

public interface ICommentDao {
    int createComment(Comment comment);

    int updateComment(Comment comment);

    int deleteComment(Long id, String userId);

    Comment queryCommentById(Long id);

    List<Comment> queryComment(Long postId, String userId, String keyword);

    boolean commentExisting(Long id, String userId);
}
