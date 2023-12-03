package com.connect.core.service.post;

import com.connect.api.post.dto.PostDto;
import com.connect.api.post.request.CreatePostRequest;
import com.connect.api.post.request.QueryPostRequest;
import com.connect.api.post.request.UpdatePostRequest;

import java.util.List;

public interface IPostService {
    PostDto queryPostById(long id);

    List<PostDto> queryPost(QueryPostRequest request);

    void createPost(String userId, CreatePostRequest request);

    void updatePost(Long postId, UpdatePostRequest request);

    void deletePost(Long id);
}
