package com.connect.data.dao;

import com.connect.data.entity.Post;

import java.util.List;

public interface IPostDao {
    Post queryPostById(Long id);

    List<Post> queryPost(Long postId, String userId, String keyword);

    int createPost(Post post);

    int updatePost(Post post);

    int deletePost(Long id);

    boolean postExisting(Long id);
}
