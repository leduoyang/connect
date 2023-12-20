package com.connect.core.service.post;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.post.dto.*;
import com.connect.api.post.request.QueryPostRequest;

import java.util.List;

public interface IPostService {
    QueryPostResponseDto queryPostById(long id, RequestMetaInfo requestMetaInfo);

    List<QueryPostResponseDto> queryPost(QueryPostDto request, RequestMetaInfo requestMetaInfo);

    long createPost(CreatePostDto request, RequestMetaInfo requestMetaInfo);

    void updatePost(UpdatePostDto request, RequestMetaInfo requestMetaInfo);

    void deletePost(DeletePostDto request, RequestMetaInfo requestMetaInfo);
}
