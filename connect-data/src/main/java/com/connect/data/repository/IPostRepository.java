package com.connect.data.repository;

import com.connect.data.entity.Post;
import com.connect.data.param.QueryPostParam;

import java.util.List;

public interface IPostRepository {
    void createPost(Post post);

    void updatePost(Post post);

    void incrementViews(long id, int version);

    void refreshStars(long id, int version, int stars);

    void deletePost(Post post);

    Post queryPostById(long id);

    List<Post> queryPost(QueryPostParam param);
}
