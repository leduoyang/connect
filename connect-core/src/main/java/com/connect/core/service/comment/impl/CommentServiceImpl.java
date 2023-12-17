package com.connect.core.service.comment.impl;

import com.connect.api.comment.dto.*;
import com.connect.api.comment.request.QueryCommentRequest;
import com.connect.api.common.RequestMetaInfo;
import com.connect.core.service.comment.ICommentService;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.data.entity.Comment;
import com.connect.data.param.QueryCommentParam;
import com.connect.data.repository.ICommentRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class CommentServiceImpl implements ICommentService {
    private ICommentRepository commentRepository;

    public CommentServiceImpl(ICommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public QueryCommentResponseDto queryCommentById(long id, RequestMetaInfo requestMetaInfo) {
        Comment comment = commentRepository.queryCommentById(id, requestMetaInfo.getUserId());
        if (comment == null) {
            log.error("query comment not found or not authorized to retrieve");
            return null;
        }

        commentRepository.incrementViews(
                comment.getId(),
                comment.getVersion()
        );

        QueryCommentResponseDto commentDto = new QueryCommentResponseDto()
                .setId(comment.getId())
                .setPostId(comment.getPostId())
                .setStatus(comment.getStatus())
                .setContent(comment.getContent())
                .setStars(comment.getStars())
                .setViews(comment.getViews())
                .setCreatedUser(comment.getCreatedUser())
                .setUpdatedUser(comment.getUpdatedUser())
                .setDbCreateTime(comment.getDbCreateTime())
                .setDbModifyTime(comment.getDbModifyTime());

        return commentDto;
    }

    @Override
    public List<QueryCommentResponseDto> queryComment(QueryCommentRequest request, RequestMetaInfo requestMetaInfo) {
        QueryCommentParam param = new QueryCommentParam()
                .setPostId(request.getPostId())
                .setUserId(request.getUserId())
                .setKeyword(request.getKeyword())
                .setTags(request.getTags());

        List<Comment> commentList = commentRepository.queryComment(param, requestMetaInfo.getUserId());

        return commentList
                .stream()
                .map(x -> new QueryCommentResponseDto()
                        .setId(x.getId())
                        .setPostId(x.getPostId())
                        .setStatus(x.getStatus())
                        .setContent(x.getContent())
                        .setStars(x.getStars())
                        .setViews(x.getViews())
                        .setCreatedUser(x.getCreatedUser())
                        .setUpdatedUser(x.getUpdatedUser())
                        .setDbCreateTime(x.getDbCreateTime())
                        .setDbModifyTime(x.getDbModifyTime()))
                .collect(Collectors.toList());
    }

    @Override
    public void createComment(CreateCommentDto request) {
        if (request.getStatus() < 0 || request.getStatus() > 2) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload (status should be between 0 and 2)"
            );
        }

        Comment comment = new Comment()
                .setStatus(request.getStatus())
                .setPostId(request.getPostId())
                .setCreatedUser(request.getCreatedUser())
                .setUpdatedUser(request.getCreatedUser());
        if (request.getContent() == null || request.getContent().equals("")) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload (comment content can not be empty)"
            );
        }
        comment.setContent(request.getContent());

        commentRepository.createComment(comment);
    }

    @Override
    public void updateComment(UpdateCommentDto request) {
        Comment comment = new Comment()
                .setId(request.getId())
                .setUpdatedUser(request.getUpdatedUser());
        if (request.getStatus() != null) {
            if (request.getStatus() < 0 || request.getStatus() > 2) {
                throw new ConnectDataException(
                        ConnectErrorCode.PARAM_EXCEPTION,
                        "Invalid payload (status should be between 0 and 2)"
                );
            }
            comment.setStatus(request.getStatus());
        }
        if (request.getContent() != null) {
            if (request.getContent().equals("")) {
                throw new ConnectDataException(
                        ConnectErrorCode.PARAM_EXCEPTION,
                        "Invalid payload (comment content can not be blank)"
                );
            }
            comment.setContent(request.getContent());
        }

        commentRepository.updateComment(comment);
    }

    @Override
    public void deleteComment(DeleteCommentDto request) {
        Comment comment = new Comment()
                .setId(request.getId())
                .setUpdatedUser(request.getUpdatedUser());

        commentRepository.deleteComment(comment);
    }

}
