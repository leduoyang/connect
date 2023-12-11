package com.connect.data.dao;

import com.connect.data.entity.Post;

import java.util.List;

public interface IPostDao {
    int createPost(Post post);

    int updatePost(Post post);

    int deletePost(Long id, String userId);

    Post queryPostById(Long id);

    List<Post> queryPost(Long postId, String userId, String keyword);

    boolean postExisting(Long id, String userId);
}
