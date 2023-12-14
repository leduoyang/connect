package com.connect.data.dao;

import com.connect.data.entity.Post;

import java.util.List;

public interface IPostDao {
    int createPost(Post post);

    int updatePost(Post post);

    int incrementViews(long id, int version);

    int refreshStars(long id, int version, int stars);

    int deletePost(long id, String userId);

    Post queryPostById(long id);

    List<Post> queryPost(Long postId, String userId, String keyword, String tags);

    boolean postExisting(long id, String userId);
}
