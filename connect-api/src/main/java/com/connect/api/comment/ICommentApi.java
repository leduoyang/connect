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
    APIResponse<QueryCommentResponse> queryComment(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @NotNull @PathVariable Long commentId
    );

    @GetMapping(value = "/comment")
    APIResponse<QueryCommentResponse> queryCommentWithFilter(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated QueryCommentRequest request
    );

    @PostMapping(value = "/comment")
    APIResponse<Long> createComment(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated @RequestBody CreateCommentRequest request
    );

    @PatchMapping(value = "/comment/{commentId}")
    APIResponse<Void> updateComment(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated @NotNull @PathVariable Long commentId,
            @Validated @RequestBody UpdateCommentRequest request
    );

    @DeleteMapping(value = "/comment/{commentId}")
    APIResponse<Void> deleteComment(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated @NotNull @PathVariable Long commentId
    );
}
