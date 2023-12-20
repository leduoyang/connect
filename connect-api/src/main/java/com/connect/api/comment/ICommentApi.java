package com.connect.api.comment;

import com.connect.api.comment.request.CreateCommentRequest;
import com.connect.api.comment.request.QueryCommentRequest;
import com.connect.api.comment.request.UpdateCommentRequest;
import com.connect.api.comment.response.QueryCommentResponse;
import com.connect.api.common.APIResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequestMapping(value = "/api/connect/v1")
public interface ICommentApi {

    @GetMapping(value = "/comment/{commentId}")
    APIResponse<QueryCommentResponse> queryComment(@NotNull @PathVariable Long commentId);

    @GetMapping(value = "/comment")
    APIResponse<QueryCommentResponse> queryCommentWithFilter(
            @Validated QueryCommentRequest request
    );

    @PostMapping(value = "/comment")
    APIResponse<Long> createComment(
            @Validated @RequestBody CreateCommentRequest request
    );

    @PatchMapping(value = "/comment/{commentId}")
    APIResponse<Void> updateComment(
            @Validated @NotNull @PathVariable Long commentId,
            @Validated @RequestBody UpdateCommentRequest request
    );

    @DeleteMapping(value = "/comment/{commentId}")
    APIResponse<Void> deleteComment(
            @Validated @NotNull @PathVariable Long commentId
    );
}
