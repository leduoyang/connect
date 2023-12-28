package com.connect.data.dao;

import com.connect.data.entity.Post;

import java.util.List;

public interface IPostDao {
    int createPost(Post post);

    int updatePost(Post post);

    int incrementViews(long id, int version);

    int refreshStars(long id, int version, int stars);

    int deletePost(long id, long userId);

    Post queryPostById(long id, long userId);

    Post internalQueryPostById(long id);

    List<Post> queryPost(String keyword, String tags, long userId);

    boolean postExisting(long id, long userId);
}
