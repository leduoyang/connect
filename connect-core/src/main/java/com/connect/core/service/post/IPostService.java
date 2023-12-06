package com.connect.core.service.post;

import com.connect.api.post.dto.CreatePostDto;
import com.connect.api.post.dto.DeletePostDto;
import com.connect.api.post.dto.QueryPostDto;
import com.connect.api.post.dto.UpdatePostDto;
import com.connect.api.post.request.QueryPostRequest;

import java.util.List;

public interface IPostService {
    QueryPostDto queryPostById(long id);

    List<QueryPostDto> queryPost(QueryPostRequest request);

    void createPost(CreatePostDto request);

    void updatePost(UpdatePostDto request);

    void deletePost(DeletePostDto request);
}
