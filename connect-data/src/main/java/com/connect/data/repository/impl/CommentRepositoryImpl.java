package com.connect.data.repository.impl;

import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.data.dao.ICommentDao;
import com.connect.data.entity.Comment;
import com.connect.data.entity.Comment;
import com.connect.data.entity.Post;
import com.connect.data.param.QueryCommentParam;
import com.connect.data.param.QueryPostParam;
import com.connect.data.repository.ICommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class CommentRepositoryImpl implements ICommentRepository {

    @Autowired
    ICommentDao commentDao;

    public CommentRepositoryImpl(ICommentDao commentDao) {
        this.commentDao = commentDao;
    }

    public Comment queryCommentById(long id) {
        return commentDao.queryCommentById(id);
    }

    public List<Comment> queryComment(QueryCommentParam param) {
        return commentDao.queryComment(
                param.getPostId(),
                param.getUserId(),
                param.getKeyword()
        );
    }

    public void createComment(Comment comment) {
        int affected = commentDao.createComment(comment);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.POST_CREATE_EXCEPTION);
        }
    }

    public void updateComment(Comment comment) {
        long targetId = comment.getId();
        String userId = comment.getUpdatedUser();
        boolean existed = commentDao.commentExisting(targetId, userId);
        if (!existed) {
            throw new ConnectDataException(
                    ConnectErrorCode.POST_NOT_EXISTED_EXCEPTION,
                    String.format("Post %s not exited or user %s is not the creator", targetId, userId)
            );
        }

        int affected = commentDao.updateComment(comment);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.POST_UPDATE_EXCEPTION);
        }
    }

    public void deleteComment(Comment comment) {
        long targetId = comment.getId();
        String userId = comment.getUpdatedUser();
        boolean existed = commentDao.commentExisting(targetId, userId);
        if (!existed) {
            throw new ConnectDataException(
                    ConnectErrorCode.POST_NOT_EXISTED_EXCEPTION,
                    String.format("Post %s not exited or user %s is not the creator", targetId, userId)
            );
        }

        int affected = commentDao.deleteComment(targetId);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.POST_UPDATE_EXCEPTION);
        }
    }
}
