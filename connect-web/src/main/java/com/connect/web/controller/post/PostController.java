package com.connect.web.controller.post;

import com.connect.api.post.IPostApi;
import com.connect.api.post.dto.PostDto;
import com.connect.api.post.response.QueryPostResponse;
import com.connect.api.common.APIResponse;
import com.connect.api.post.request.CreatePostRequest;
import com.connect.api.post.request.QueryPostRequest;
import com.connect.api.post.request.UpdatePostRequest;
import com.connect.core.service.post.IPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class PostController implements IPostApi {
    private final IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @Override
    public APIResponse<QueryPostResponse> queryPost(Long postId) {
        PostDto postDto = postService.queryPostById(postId);
        List<PostDto> postDtoList = new ArrayList<>();
        postDtoList.add(postDto);

        QueryPostResponse response = new QueryPostResponse()
                .setItems(postDtoList)
                .setTotal(postDtoList.size());

        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryPostResponse> queryPostWithFilter(
            @Validated QueryPostRequest request
    ) {
        List<PostDto> postDtoList = postService.queryPost(request);

        QueryPostResponse response = new QueryPostResponse()
                .setItems(postDtoList)
                .setTotal(postDtoList.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<Void> createPost(
            @Validated @RequestBody CreatePostRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        postService.createPost(authentication.getName(), request);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> updatePost(
            @Validated @NotNull @PathVariable Long postId,
            @Validated @RequestBody UpdatePostRequest request
    ) {
        postService.updatePost(postId, request);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> deletePost(
            @Validated @NotNull @PathVariable Long postId
    ) {
        postService.deletePost(postId);
        return APIResponse.getOKJsonResult(null);
    }
}
