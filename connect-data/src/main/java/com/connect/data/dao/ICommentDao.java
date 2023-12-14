package com.connect.data.dao;

import com.connect.data.entity.Comment;

import java.util.List;

public interface ICommentDao {
    int createComment(Comment comment);

    int updateComment(Comment comment);

    int incrementViewCount(long id, int version);

    int refreshLikeCount(long id, int version, int likesCount);

    int deleteComment(long id, String userId);

    Comment queryCommentById(long id);

    List<Comment> queryComment(Long postId, String userId, String keyword, String tags);

    boolean commentExisting(long id, String userId);
}
