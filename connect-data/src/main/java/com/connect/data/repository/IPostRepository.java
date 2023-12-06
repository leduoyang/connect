package com.connect.data.repository;

import com.connect.data.entity.Post;
import com.connect.data.param.QueryPostParam;

import java.util.List;

public interface IPostRepository {
    void createPost(Post post);

    void deletePost(Post post);

    List<Post> queryPost(QueryPostParam param);

    Post queryPostById(long id);

    void updatePost(Post post);
}
