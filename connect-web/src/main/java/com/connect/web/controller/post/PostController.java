package com.connect.web.controller.post;

import com.connect.api.post.IPostApi;
import com.connect.api.post.dto.CreatePostDto;
import com.connect.api.post.dto.DeletePostDto;
import com.connect.api.post.dto.QueryPostDto;
import com.connect.api.post.dto.UpdatePostDto;
import com.connect.api.post.response.QueryPostResponse;
import com.connect.api.common.APIResponse;
import com.connect.api.post.request.CreatePostRequest;
import com.connect.api.post.request.QueryPostRequest;
import com.connect.api.post.request.UpdatePostRequest;
import com.connect.core.service.post.IPostService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@Validated
public class PostController implements IPostApi {
    private final IPostService postService;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @Override
    public APIResponse<QueryPostResponse> queryPost(Long postId) {
        QueryPostDto postDto = postService.queryPostById(postId);
        List<QueryPostDto> postDtoList = new ArrayList<>();
        postDtoList.add(postDto);

        QueryPostResponse response = new QueryPostResponse()
                .setItems(postDtoList)
                .setTotal(postDtoList.size());

        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryPostResponse> queryPostWithFilter(
            QueryPostRequest request
    ) {
        List<QueryPostDto> postDtoList = postService.queryPost(request);

        QueryPostResponse response = new QueryPostResponse()
                .setItems(postDtoList)
                .setTotal(postDtoList.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<Void> createPost(
            @RequestBody CreatePostRequest request
    ) {
        CreatePostDto createPostDto = new CreatePostDto();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(request, createPostDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        createPostDto.setCreatedUser(authentication.getName());

        postService.createPost(createPostDto);

        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> updatePost(
            @PathVariable Long postId,
            @RequestBody UpdatePostRequest request
    ) {
        UpdatePostDto updatePostDto = new UpdatePostDto();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(request, updatePostDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        updatePostDto.setUpdatedUser(authentication.getName());
        updatePostDto.setId(postId);

        postService.updatePost(updatePostDto);

        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> deletePost(
            @PathVariable Long postId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        DeletePostDto deletePostDto = new DeletePostDto()
                .setId(postId)
                .setUpdatedUser(authentication.getName());
        postService.deletePost(deletePostDto);

        return APIResponse.getOKJsonResult(null);
    }
}
