package com.connect.data.dao;

import com.connect.data.entity.Comment;

import java.util.List;

public interface ICommentDao {
    int createComment(Comment comment);

    int updateComment(Comment comment);

    int incrementViews(long id, int version);

    int refreshStars(long id, int version, int stars);

    int deleteComment(long id, long userId);

    Comment queryCommentById(long id, long userId);

    Comment internalQueryCommentById(long id);

    List<Comment> queryComment(String keyword, String tags, long userId);

    boolean commentExisting(long id, long userId);
}
