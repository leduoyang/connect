package com.connect.web.controller.post;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.post.IPostApi;
import com.connect.api.common.APIResponse;
import com.connect.api.post.request.CreatePostRequest;
import com.connect.api.post.request.QueryPostRequest;
import com.connect.api.post.request.UpdatePostRequest;
import com.connect.api.post.vo.QueryPostVo;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.follow.IFollowService;
import com.connect.core.service.post.IPostService;
import lombok.extern.slf4j.Slf4j;
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
    public APIResponse<com.connect.api.post.response.QueryPostVo> queryPost(Long postId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        QueryPostVo postResponseDto = postService.queryPostById(postId, requestMetaInfo);
        if (postResponseDto == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                    "target post not found or not authorized to retrieve"
            );
        }

        List<QueryPostVo> postDtoList = new ArrayList<>();
        postDtoList.add(postResponseDto);

        com.connect.api.post.response.QueryPostVo response = new com.connect.api.post.response.QueryPostVo()
                .setItems(postDtoList)
                .setTotal(postDtoList.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<com.connect.api.post.response.QueryPostVo> queryPostWithFilter(
            QueryPostRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        List<QueryPostVo> postResponseDto = postService.queryPost(request, requestMetaInfo);

        com.connect.api.post.response.QueryPostVo response = new com.connect.api.post.response.QueryPostVo()
                .setItems(postResponseDto)
                .setTotal(postResponseDto.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<Long> createPost(
            @RequestBody CreatePostRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        if (request.getContent() == null && request.getReferenceId() == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload (post content and referenceId can not both be absent)"
            );
        }
        Long id = postService.createPost(request, requestMetaInfo);
        return APIResponse.getOKJsonResult(id);
    }

    @Override
    public APIResponse<Void> updatePost(
            @PathVariable Long postId,
            @RequestBody UpdatePostRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        if (request.getContent() != null && request.getContent().equals("")) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload (post content can not be blank)"
            );
        }
        postService.updatePost(postId, request, requestMetaInfo);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> deletePost(
            @PathVariable Long postId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        postService.deletePost(postId, requestMetaInfo);
        return APIResponse.getOKJsonResult(null);
    }
}
