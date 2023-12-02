package com.connect.web.controller.comment;

import com.connect.api.comment.ICommentApi;
import com.connect.api.comment.dto.CommentDto;
import com.connect.api.comment.request.CreateCommentRequest;
import com.connect.api.comment.request.QueryCommentRequest;
import com.connect.api.comment.request.UpdateCommentRequest;
import com.connect.api.comment.response.QueryCommentResponse;
import com.connect.api.common.APIResponse;
import com.connect.core.service.comment.ICommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
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
        CommentDto commentDto = commentService.queryCommentById(commentId);
        List<CommentDto> commentDtoList = new ArrayList<>();
        commentDtoList.add(commentDto);

        QueryCommentResponse response = new QueryCommentResponse().setItems(commentDtoList).setTotal(commentDtoList.size());

        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryCommentResponse> queryCommentWithFilter(
            @Validated QueryCommentRequest request
    ) {
        List<CommentDto> commentDtoList = commentService.queryComment(request);

        QueryCommentResponse response = new QueryCommentResponse()
                .setItems(commentDtoList)
                .setTotal(commentDtoList.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<Void> createComment(
            @Validated @RequestBody CreateCommentRequest request
    ) {
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> updateComment(
            @Validated @NotNull @PathVariable Long commentId,
            @Validated @RequestBody UpdateCommentRequest request
    ) {
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> deleteComment(
            @Validated @NotNull @PathVariable Long commentId
    ) {
        return APIResponse.getOKJsonResult(null);
    }
}
