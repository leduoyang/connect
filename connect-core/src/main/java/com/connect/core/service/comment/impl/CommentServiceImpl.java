package com.connect.core.service.comment.impl;

import com.connect.api.comment.request.CreateCommentRequest;
import com.connect.api.comment.request.QueryCommentRequest;
import com.connect.api.comment.request.UpdateCommentRequest;
import com.connect.api.comment.vo.QueryCommentVo;
import com.connect.api.common.RequestMetaInfo;
import com.connect.core.service.comment.ICommentService;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.data.dto.CommentDto;
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
    public QueryCommentVo queryCommentById(long id, RequestMetaInfo requestMetaInfo) {
        CommentDto comment = commentRepository.queryCommentById(id, requestMetaInfo.getUserId());
        if (comment == null) {
            log.error("query comment not found or not authorized to retrieve");
            return null;
        }

        commentRepository.incrementViews(
                comment.getId(),
                comment.getVersion()
        );

        QueryCommentVo commentDto = new QueryCommentVo()
                .setId(comment.getId())
                .setPostId(comment.getPostId())
                .setStatus(comment.getStatus())
                .setContent(comment.getContent())
                .setStars(comment.getStars())
                .setViews(comment.getViews())
                .setUsername(comment.getUsername())
                .setDbModifyTime(comment.getDbModifyTime());

        return commentDto;
    }

    @Override
    public List<QueryCommentVo> queryComment(QueryCommentRequest request, RequestMetaInfo requestMetaInfo) {
        QueryCommentParam param = new QueryCommentParam()
                .setUsername(request.getUsername())
                .setKeyword(request.getKeyword())
                .setTags(request.getTags());

        List<CommentDto> commentList = commentRepository.queryComment(param, requestMetaInfo.getUserId());

        return commentList
                .stream()
                .map(x -> new QueryCommentVo()
                        .setId(x.getId())
                        .setPostId(x.getPostId())
                        .setStatus(x.getStatus())
                        .setContent(x.getContent())
                        .setStars(x.getStars())
                        .setViews(x.getViews())
                        .setUsername(x.getUsername())
                        .setDbModifyTime(x.getDbModifyTime()))
                .collect(Collectors.toList());
    }

    @Override
    public long createComment(CreateCommentRequest request, RequestMetaInfo requestMetaInfo) {
        if (request.getStatus() < 0 || request.getStatus() > 2) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload (status should be between 0 and 2)"
            );
        }

        Comment comment = new Comment()
                .setStatus(request.getStatus())
                .setPostId(request.getPostId())
                .setCreatedUser(requestMetaInfo.getUserId())
                .setUpdatedUser(requestMetaInfo.getUserId());
        if (request.getContent() == null || request.getContent().equals("")) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload (comment content can not be empty)"
            );
        }
        comment.setContent(request.getContent());

        return commentRepository.createComment(comment);
    }

    @Override
    public void updateComment(long id, UpdateCommentRequest request, RequestMetaInfo requestMetaInfo) {
        Comment comment = new Comment()
                .setId(id)
                .setUpdatedUser(requestMetaInfo.getUserId());
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
    public void deleteComment(long id, RequestMetaInfo requestMetaInfo) {
        Comment comment = new Comment()
                .setId(id)
                .setUpdatedUser(requestMetaInfo.getUserId());

        commentRepository.deleteComment(comment);
    }

}
