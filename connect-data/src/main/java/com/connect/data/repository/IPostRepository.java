package com.connect.data.repository;

import com.connect.data.entity.Post;
import com.connect.data.param.QueryPostParam;

import java.util.List;

public interface IPostRepository {
    void createPost(Post post);

    void updatePost(Post post);

    void incrementViewCount(long id, int version);

    void refreshLikeCount(long id, int version, int likesCount);

    void deletePost(Post post);

    Post queryPostById(long id);

    List<Post> queryPost(QueryPostParam param);
}
