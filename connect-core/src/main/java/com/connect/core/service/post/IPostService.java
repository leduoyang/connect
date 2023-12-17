package com.connect.core.service.post;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.post.dto.*;
import com.connect.api.post.request.QueryPostRequest;

import java.util.List;

public interface IPostService {
    QueryPostResponseDto queryPostById(long id, RequestMetaInfo requestMetaInfo);

    List<QueryPostResponseDto> queryPost(QueryPostDto request, RequestMetaInfo requestMetaInfo);

    void createPost(CreatePostDto request);

    void updatePost(UpdatePostDto request);

    void deletePost(DeletePostDto request);
}
