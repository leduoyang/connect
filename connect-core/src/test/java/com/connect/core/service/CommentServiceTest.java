package com.connect.core.service;

import com.connect.api.comment.dto.CreateCommentDto;
import com.connect.api.comment.dto.UpdateCommentDto;
import com.connect.common.enums.CommentStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.core.service.comment.impl.CommentServiceImpl;
import com.connect.data.entity.Comment;
import com.connect.data.repository.ICommentRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = CommentServiceImpl.class)
public class CommentServiceTest {
    @MockBean
    private ICommentRepository commentRepository;

    @Autowired
    private CommentServiceImpl commentService;

    @Captor
    private ArgumentCaptor<Comment> commentCaptor;

    @Test
    public void test_create_comment_without_status_should_use_default_value() {
        CreateCommentDto createCommentDto = new CreateCommentDto();
        createCommentDto.setContent("test comment service");

        commentService.createComment(createCommentDto);

        verify(commentRepository, times(1)).createComment(commentCaptor.capture());
        Comment capturedComment = commentCaptor.getValue();

        assertEquals(CommentStatus.PUBLIC.getCode(), capturedComment.getStatus());
    }

    @Test
    public void test_create_comment_with_invalid_status_should_raise_exception() {
        CreateCommentDto createCommentDto = new CreateCommentDto();
        createCommentDto.setContent("test comment service");
        createCommentDto.setStatus(-1);

        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            commentService.createComment(createCommentDto);
        });

        assertEquals("Parameters error:Invalid payload (status should be between 0 and 3)", expectedException.getErrorMsg());
    }

    @Test
    public void test_create_comment_with_valid_status_should_create_with_target_status() {
        int targetStatus = CommentStatus.PRIVATE.getCode();
        CreateCommentDto createCommentDto = new CreateCommentDto();
        createCommentDto.setContent("test comment service");
        createCommentDto.setStatus(targetStatus);

        commentService.createComment(createCommentDto);

        verify(commentRepository, times(1)).createComment(commentCaptor.capture());
        Comment capturedComment = commentCaptor.getValue();
        ;
        assertEquals(targetStatus, capturedComment.getStatus());
    }

    @Test
    public void test_update_comment_without_status_should_avoid_default_value() {
        UpdateCommentDto updateCommentDto = new UpdateCommentDto();

        commentService.updateComment(updateCommentDto);

        verify(commentRepository, times(1)).updateComment(commentCaptor.capture());
        Comment capturedComment = commentCaptor.getValue();
        assertNull(capturedComment.getStatus());
    }

    @Test
    public void test_update_comment_with_invalid_status_should_raise_exception() {
        UpdateCommentDto updateCommentDto = new UpdateCommentDto();
        updateCommentDto.setStatus(-1);

        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            commentService.updateComment(updateCommentDto);
        });

        assertEquals("Parameters error:Invalid payload (status should be between 0 and 3)", expectedException.getErrorMsg());
    }

    @Test
    public void test_update_comment_with_valid_status_should_update_with_target_status() {
        int targetStatus = CommentStatus.PRIVATE.getCode();
        UpdateCommentDto updateCommentDto = new UpdateCommentDto();
        updateCommentDto.setStatus(targetStatus);

        commentService.updateComment(updateCommentDto);

        verify(commentRepository, times(1)).updateComment(commentCaptor.capture());
        Comment capturedComment = commentCaptor.getValue();
        assertEquals(targetStatus, capturedComment.getStatus());
    }
}

