package com.connect.web.controller.comment;

import com.connect.api.comment.ICommentApi;
import com.connect.api.comment.dto.*;
import com.connect.api.comment.request.CreateCommentRequest;
import com.connect.api.comment.request.QueryCommentRequest;
import com.connect.api.comment.request.UpdateCommentRequest;
import com.connect.api.comment.response.QueryCommentResponse;
import com.connect.api.common.APIResponse;
import com.connect.api.common.RequestMetaInfo;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.comment.ICommentService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class CommentController implements ICommentApi {
    private final ICommentService commentService;

    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public APIResponse<QueryCommentResponse> queryComment(Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(authentication.getName())
                .setDetails(authentication.getDetails());

        QueryCommentResponseDto commentDto = commentService.queryCommentById(commentId, requestMetaInfo);
        if (commentDto == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                    "target comment not found or not authorized to retrieve"
            );
        }

        List<QueryCommentResponseDto> commentDtoList = new ArrayList<>();
        commentDtoList.add(commentDto);

        QueryCommentResponse response = new QueryCommentResponse()
                .setItems(commentDtoList)
                .setTotal(commentDtoList.size());

        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryCommentResponse> queryCommentWithFilter(
            QueryCommentRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(authentication.getName())
                .setDetails(authentication.getDetails());

        List<QueryCommentResponseDto> commentDtoList = commentService.queryComment(request, requestMetaInfo);

        QueryCommentResponse response = new QueryCommentResponse()
                .setItems(commentDtoList)
                .setTotal(commentDtoList.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<Long> createComment(
            @RequestBody CreateCommentRequest request
    ) {
        CreateCommentDto createCommentDto = new CreateCommentDto();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(request, createCommentDto);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(authentication.getName())
                .setDetails(authentication.getDetails());

        Long id = commentService.createComment(createCommentDto, requestMetaInfo);

        return APIResponse.getOKJsonResult(id);
    }

    @Override
    public APIResponse<Void> updateComment(
            @PathVariable Long commentId,
            @RequestBody UpdateCommentRequest request
    ) {
        UpdateCommentDto updateCommentDto = new UpdateCommentDto();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(request, updateCommentDto);
        updateCommentDto.setId(commentId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(authentication.getName())
                .setDetails(authentication.getDetails());

        commentService.updateComment(updateCommentDto, requestMetaInfo);

        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> deleteComment(
            @PathVariable Long commentId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(authentication.getName())
                .setDetails(authentication.getDetails());

        DeleteCommentDto deleteCommentDto = new DeleteCommentDto()
                .setId(commentId);
        commentService.deleteComment(deleteCommentDto, requestMetaInfo);

        return APIResponse.getOKJsonResult(null);
    }
}
