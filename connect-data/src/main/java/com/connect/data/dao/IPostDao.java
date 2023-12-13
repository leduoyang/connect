package com.connect.data.dao;

import com.connect.data.entity.Post;

import java.util.List;

public interface IPostDao {
    int createPost(Post post);

    int updatePost(Post post);

    int deletePost(long id, String userId);

    Post queryPostById(long id);

    List<Post> queryPost(long postId, String userId, String keyword, String tags);

    boolean postExisting(long id, String userId);
}
