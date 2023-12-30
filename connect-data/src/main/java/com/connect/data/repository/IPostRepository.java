package com.connect.data.repository;

import com.connect.api.common.RequestMetaInfo;
import com.connect.data.dto.PostDto;
import com.connect.data.entity.Post;
import com.connect.data.param.QueryPostParam;

import java.util.List;

public interface IPostRepository {
    long createPost(Post post);

    void updatePost(Post post);

    void incrementViews(long id, int version);

    void refreshStars(long id, int version, int stars);

    void deletePost(Post post);

    PostDto queryPostById(long id, long userId);

    Post internalQueryPostById(long id);

    List<PostDto> queryPost(QueryPostParam param, long userId);
}
