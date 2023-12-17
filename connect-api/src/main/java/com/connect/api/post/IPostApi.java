package com.connect.api.post;

import com.connect.api.post.dto.QueryPostDto;
import com.connect.api.post.response.QueryPostResponse;
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
    APIResponse<QueryPostResponse> queryPost(@NotNull @PathVariable Long postId);

    @GetMapping(value = "/post")
    APIResponse<QueryPostResponse> queryPostWithFilter(
            @Validated QueryPostRequest request
    );

    @PostMapping(value = "/post")
    APIResponse<Void> createPost(
            @Validated @RequestBody CreatePostRequest request
    );

    @PatchMapping(value = "/post/{postId}")
    APIResponse<Void> updatePost(
            @Validated @NotNull @PathVariable Long postId,
            @Validated @RequestBody UpdatePostRequest request
    );

    @DeleteMapping(value = "/post/{postId}")
    APIResponse<Void> deletePost(
            @Validated @NotNull @PathVariable Long postId
    );
}
