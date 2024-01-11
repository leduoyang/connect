package com.connect.core.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.post.request.CreatePostRequest;
import com.connect.api.post.request.UpdatePostRequest;
import com.connect.common.enums.PostStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.core.service.post.impl.PostServiceImpl;
import com.connect.data.entity.Post;
import com.connect.data.repository.IPostRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = PostServiceImpl.class)
public class PostServiceTest {
    @MockBean
    private IPostRepository postRepository;

    @Autowired
    private PostServiceImpl postService;

    @Captor
    private ArgumentCaptor<Post> postCaptor;

    @Test
    public void test_create_post_without_status_should_use_default_value() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);
        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setContent("test post service");

        postService.createPost(createPostRequest, requestMetaInfo);

        verify(postRepository, times(1)).createPost(postCaptor.capture());
        Post capturedPost = postCaptor.getValue();

        assertEquals(PostStatus.PUBLIC.getCode(), capturedPost.getStatus());
    }

    @Test
    public void test_create_post_with_invalid_status_should_raise_exception() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);
        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setContent("test post service");
        createPostRequest.setStatus(-1);

        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            postService.createPost(createPostRequest, requestMetaInfo);
        });

        assertEquals("Parameters error:Invalid payload (status should be between 0 and 2)", expectedException.getErrorMsg());
    }

    @Test
    public void test_create_post_with_valid_status_should_create_with_target_status() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);
        int targetStatus = PostStatus.PRIVATE.getCode();
        CreatePostRequest createPostRequest = new CreatePostRequest();
        createPostRequest.setContent("test post service");
        createPostRequest.setStatus(targetStatus);

        postService.createPost(createPostRequest, requestMetaInfo);

        verify(postRepository, times(1)).createPost(postCaptor.capture());
        Post capturedPost = postCaptor.getValue();
        ;
        assertEquals(targetStatus, capturedPost.getStatus());
    }

    @Test
    public void test_update_post_without_status_should_avoid_default_value() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();

        postService.updatePost(1L, updatePostRequest, requestMetaInfo);

        verify(postRepository, times(1)).updatePost(postCaptor.capture());
        Post capturedPost = postCaptor.getValue();
        assertNull(capturedPost.getStatus());
    }

    @Test
    public void test_update_post_with_invalid_status_should_raise_exception() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setStatus(-1);

        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            postService.updatePost(1L, updatePostRequest, requestMetaInfo);
        });

        assertEquals("Parameters error:Invalid payload (status should be between 0 and 2)", expectedException.getErrorMsg());
    }

    @Test
    public void test_update_post_with_valid_status_should_update_with_target_status() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);
        int targetStatus = PostStatus.PRIVATE.getCode();
        UpdatePostRequest updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setStatus(targetStatus);

        postService.updatePost(1L, updatePostRequest, requestMetaInfo);

        verify(postRepository, times(1)).updatePost(postCaptor.capture());
        Post capturedPost = postCaptor.getValue();
        assertEquals(targetStatus, capturedPost.getStatus());
    }
}

