package com.connect.core.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.post.dto.CreatePostDto;
import com.connect.api.post.dto.UpdatePostDto;
import com.connect.common.enums.PostStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.core.service.post.iml.PostServiceImpl;
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
                .setUserId("ROOT");
        CreatePostDto createPostDto = new CreatePostDto();
        createPostDto.setContent("test post service");

        postService.createPost(createPostDto, requestMetaInfo);

        verify(postRepository, times(1)).createPost(postCaptor.capture());
        Post capturedPost = postCaptor.getValue();

        assertEquals(PostStatus.PUBLIC.getCode(), capturedPost.getStatus());
    }

    @Test
    public void test_create_post_with_invalid_status_should_raise_exception() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId("ROOT");
        CreatePostDto createPostDto = new CreatePostDto();
        createPostDto.setContent("test post service");
        createPostDto.setStatus(-1);

        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            postService.createPost(createPostDto, requestMetaInfo);
        });

        assertEquals("Parameters error:Invalid payload (status should be between 0 and 3)", expectedException.getErrorMsg());
    }

    @Test
    public void test_create_post_with_valid_status_should_create_with_target_status() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId("ROOT");
        int targetStatus = PostStatus.PRIVATE.getCode();
        CreatePostDto createPostDto = new CreatePostDto();
        createPostDto.setContent("test post service");
        createPostDto.setStatus(targetStatus);

        postService.createPost(createPostDto, requestMetaInfo);

        verify(postRepository, times(1)).createPost(postCaptor.capture());
        Post capturedPost = postCaptor.getValue();
        ;
        assertEquals(targetStatus, capturedPost.getStatus());
    }

    @Test
    public void test_update_post_without_status_should_avoid_default_value() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId("ROOT");
        UpdatePostDto updatePostDto = new UpdatePostDto();

        postService.updatePost(updatePostDto, requestMetaInfo);

        verify(postRepository, times(1)).updatePost(postCaptor.capture());
        Post capturedPost = postCaptor.getValue();
        assertNull(capturedPost.getStatus());
    }

    @Test
    public void test_update_post_with_invalid_status_should_raise_exception() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId("ROOT");
        UpdatePostDto updatePostDto = new UpdatePostDto();
        updatePostDto.setStatus(-1);

        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            postService.updatePost(updatePostDto, requestMetaInfo);
        });

        assertEquals("Parameters error:Invalid payload (status should be between 0 and 3)", expectedException.getErrorMsg());
    }

    @Test
    public void test_update_post_with_valid_status_should_update_with_target_status() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId("ROOT");
        int targetStatus = PostStatus.PRIVATE.getCode();
        UpdatePostDto updatePostDto = new UpdatePostDto();
        updatePostDto.setStatus(targetStatus);

        postService.updatePost(updatePostDto, requestMetaInfo);

        verify(postRepository, times(1)).updatePost(postCaptor.capture());
        Post capturedPost = postCaptor.getValue();
        assertEquals(targetStatus, capturedPost.getStatus());
    }
}

