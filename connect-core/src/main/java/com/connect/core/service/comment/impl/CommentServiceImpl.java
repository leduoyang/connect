package com.connect.core.service.comment.impl;

import com.connect.api.comment.dto.CommentDto;
import com.connect.api.comment.request.CreateCommentRequest;
import com.connect.api.comment.request.QueryCommentRequest;
import com.connect.api.comment.request.UpdateCommentRequest;
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
    public CommentDto queryCommentById(long id) {
        Comment comment = commentRepository.queryCommentById(id);

        CommentDto commentDto = new CommentDto()
                .setId(comment.getId())
                .setContent(comment.getContent())
                .setUpdatedUser(comment.getUpdatedUser())
                .setDbModifyTime(comment.getDbModifyTime());

        return commentDto;
    }

    @Override
    public List<CommentDto> queryComment(QueryCommentRequest request) {
        QueryCommentParam param = new QueryCommentParam()
                .setPostId(request.getPostId())
                .setUserId(request.getUserId())
                .setKeyword(request.getKeyword());

        List<Comment> commentList = commentRepository.queryComment(param);

        return commentList
                .stream()
                .map(x -> new CommentDto()
                        .setId(x.getId())
                        .setContent(x.getContent())
                        .setUpdatedUser(x.getUpdatedUser())
                        .setDbModifyTime(x.getDbModifyTime()))
                .collect(Collectors.toList());
    }

    @Override
    public void createComment(CreateCommentRequest request) {
        Comment comment = new Comment()
                .setCreatedUser(request.getUserId())
                .setUpdatedUser(request.getUserId());
        if (request.getContent() == null || request.getContent().equals("")) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload (content can not be empty)"
            );
        }
        comment.setContent(request.getContent());

        commentRepository.createComment(comment);
    }

    @Override
    public void updateComment(Long commentId, UpdateCommentRequest request) {
        Comment comment = new Comment()
                .setId(commentId)
                .setContent(request.getContent())
                .setUpdatedUser(request.getUpdatedUser());
        commentRepository.updateComment(comment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteComment(id);
    }

}
