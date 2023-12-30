package com.connect.core.service.post;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.post.request.CreatePostRequest;
import com.connect.api.post.request.QueryPostRequest;
import com.connect.api.post.request.UpdatePostRequest;
import com.connect.api.post.vo.QueryPostVo;

import java.util.List;

public interface IPostService {
    QueryPostVo queryPostById(long id, RequestMetaInfo requestMetaInfo);

    List<QueryPostVo> queryPost(QueryPostRequest request, RequestMetaInfo requestMetaInfo);

    long createPost(CreatePostRequest request, RequestMetaInfo requestMetaInfo);

    void updatePost(long id, UpdatePostRequest request, RequestMetaInfo requestMetaInfo);

    void deletePost(long id, RequestMetaInfo requestMetaInfo);
}
