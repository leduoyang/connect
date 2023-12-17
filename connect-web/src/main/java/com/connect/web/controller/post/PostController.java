package com.connect.web.controller.post;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.post.IPostApi;
import com.connect.api.post.dto.*;
import com.connect.api.post.response.QueryPostResponse;
import com.connect.api.common.APIResponse;
import com.connect.api.post.request.CreatePostRequest;
import com.connect.api.post.request.QueryPostRequest;
import com.connect.api.post.request.UpdatePostRequest;
import com.connect.common.enums.FollowStatus;
import com.connect.common.enums.PostStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.follow.IFollowService;
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

    private IFollowService followService;

    public PostController(IPostService postService, IFollowService followService) {
        this.postService = postService;
        this.followService = followService;
    }

    @Override
    public APIResponse<QueryPostResponse> queryPost(Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(authentication.getName())
                .setDetails(authentication.getDetails());

        QueryPostResponseDto postResponseDto = postService.queryPostById(postId, requestMetaInfo);
        if (postResponseDto == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                    "target post not found or not authorized to retrieve"
            );
        }

        List<QueryPostResponseDto> postDtoList = new ArrayList<>();
        postDtoList.add(postResponseDto);

        QueryPostResponse response = new QueryPostResponse()
                .setItems(postDtoList)
                .setTotal(postDtoList.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryPostResponse> queryPostWithFilter(
            QueryPostRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(authentication.getName())
                .setDetails(authentication.getDetails());

        QueryPostDto postDto = new QueryPostDto()
                .setPostId(request.getPostId())
                .setKeyword(request.getKeyword())
                .setUserId(request.getUserId())
                .setTags(request.getTags())
                .setPageSize(request.getPageSize())
                .setPageIndex(request.getPageIndex());

        List<QueryPostResponseDto> postResponseDto = postService.queryPost(postDto, requestMetaInfo);

        QueryPostResponse response = new QueryPostResponse()
                .setItems(postResponseDto)
                .setTotal(postResponseDto.size());
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

        if (request.getContent() == null && request.getReferenceId() == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload (post content and referenceId can not both be absent)"
            );
        }
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
