package com.connect.api.post;

import com.connect.api.post.response.QueryPostVo;
import com.connect.api.common.APIResponse;
import com.connect.api.post.request.CreatePostRequest;
import com.connect.api.post.request.QueryPostRequest;
import com.connect.api.post.request.UpdatePostRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/connect/v1")
public interface IPostApi {

    @GetMapping(value = "/post/{postId}")
    APIResponse<QueryPostVo> queryPost(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @NotNull @PathVariable Long postId
    );

    @GetMapping(value = "/post")
    APIResponse<QueryPostVo> queryPostWithFilter(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated QueryPostRequest request
    );

    @PostMapping(value = "/post")
    APIResponse<Long> createPost(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated @RequestBody CreatePostRequest request
    );

    @PatchMapping(value = "/post/{postId}")
    APIResponse<Void> updatePost(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated @NotNull @PathVariable Long postId,
            @Validated @RequestBody UpdatePostRequest request
    );

    @DeleteMapping(value = "/post/{postId}")
    APIResponse<Void> deletePost(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated @NotNull @PathVariable Long postId
    );
}
