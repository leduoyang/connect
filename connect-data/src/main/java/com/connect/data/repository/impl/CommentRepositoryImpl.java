package com.connect.data.repository.impl;

import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.data.dao.ICommentDao;
import com.connect.data.dto.CommentDto;
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

    public CommentDto queryCommentById(long id, long userId) {
        return commentDao.queryCommentById(id, userId);
    }

    public Comment internalQueryCommentById(long id) {
        return commentDao.internalQueryCommentById(id);
    }

    public List<CommentDto> queryComment(QueryCommentParam param, long userId) {
        return commentDao.queryComment(
                param.getKeyword(),
                param.getTags(),
                param.getUsername(),
                userId
        );
    }

    public long createComment(Comment comment) {
        int affected = commentDao.createComment(comment);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.COMMENT_CREATE_EXCEPTION);
        }

        return comment.getId();
    }

    public void updateComment(Comment comment) {
        long targetId = comment.getId();
        Long userId = comment.getUpdatedUser();
        boolean existed = commentDao.commentExisting(targetId, userId);
        if (!existed) {
            throw new ConnectDataException(
                    ConnectErrorCode.COMMENT_NOT_EXISTED_EXCEPTION,
                    String.format("Post %s not exited or user %s is not the creator", targetId, userId)
            );
        }

        int affected = commentDao.updateComment(comment);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.COMMENT_UPDATE_EXCEPTION);
        }
    }

    public void incrementViews(long id, int version) {
        int affected = commentDao.incrementViews(id, version);
        if (affected <= 0) {
            log.error(ConnectErrorCode.OPTIMISTIC_LOCK_CONFLICT_EXCEPTION + "comment incrementViews failed");
        }
    }

    public void refreshStars(long id, int version, int stars) {
        int affected = commentDao.refreshStars(id, version, stars);
        if (affected <= 0) {
            log.error(ConnectErrorCode.OPTIMISTIC_LOCK_CONFLICT_EXCEPTION + "comment refreshStars failed");
        }
    }

    public void deleteComment(Comment comment) {
        long targetId = comment.getId();
        Long userId = comment.getUpdatedUser();
        boolean existed = commentDao.commentExisting(targetId, userId);
        if (!existed) {
            throw new ConnectDataException(
                    ConnectErrorCode.COMMENT_NOT_EXISTED_EXCEPTION,
                    String.format("Post %s not exited or user %s is not the creator", targetId, userId)
            );
        }

        int affected = commentDao.deleteComment(targetId, userId);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.COMMENT_UPDATE_EXCEPTION);
        }
    }
}
