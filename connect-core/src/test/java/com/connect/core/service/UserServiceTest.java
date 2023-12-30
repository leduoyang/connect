package com.connect.core.service;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.user.request.EditUserInfoRequest;
import com.connect.api.user.request.SignUpRequest;
import com.connect.common.enums.UserStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.util.ImageUploadUtil;
import com.connect.core.service.comment.ICommentService;
import com.connect.core.service.post.IPostService;
import com.connect.core.service.project.IProjectService;
import com.connect.core.service.socialLink.ISocialLinkService;
import com.connect.core.service.user.iml.UserServiceImpl;
import com.connect.data.entity.User;
import com.connect.data.repository.IFollowRepository;
import com.connect.data.repository.IStarRepository;
import com.connect.data.repository.IUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = UserServiceImpl.class)
public class UserServiceTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private ImageUploadUtil imageUploadUtil;

    @MockBean
    private IUserRepository userRepository;

    @MockBean
    private IStarRepository starRepository;

    @MockBean
    private IFollowRepository followRepository;

    @MockBean
    private IProjectService projectService;

    @MockBean
    private IPostService postService;

    @MockBean
    private ICommentService commentService;

    @MockBean
    private ISocialLinkService socialLinkService;

    @Autowired
    private UserServiceImpl userService;

    @Captor
    private ArgumentCaptor<User> postCaptor;

    @Test
    public void test_create_post_without_status_should_use_default_value() {
        SignUpRequest signUpRequest = new SignUpRequest();

        userService.signUp(signUpRequest);

        verify(userRepository, times(1)).signUp(postCaptor.capture());
        User capturedUser = postCaptor.getValue();
        assertEquals(UserStatus.PUBLIC.getCode(), capturedUser.getStatus());
    }

    @Test
    public void test_update_user_without_status_should_avoid_default_value() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);

        EditUserInfoRequest editUserInfoRequest = new EditUserInfoRequest();
        userService.editUserInfo(editUserInfoRequest, requestMetaInfo);

        verify(userRepository, times(1)).editUser(eq(requestMetaInfo.getUserId()), postCaptor.capture());
        User capturedUser = postCaptor.getValue();
        assertNull(capturedUser.getStatus());
    }

    @Test
    public void test_update_user_with_valid_status_should_edit_with_target_value() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);

        int targetStatus = UserStatus.PRIVATE.getCode();
        EditUserInfoRequest editUserInfoRequest = new EditUserInfoRequest();
        editUserInfoRequest.setStatus(targetStatus);

        userService.editUserInfo(editUserInfoRequest, requestMetaInfo);

        verify(userRepository, times(1)).editUser(eq(requestMetaInfo.getUserId()), postCaptor.capture());
        User capturedUser = postCaptor.getValue();
        assertEquals(targetStatus, capturedUser.getStatus());
    }

    @Test
    public void test_update_user_with_invalid_status_should_raise_exception() {
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(1L);

        EditUserInfoRequest editUserInfoRequest = new EditUserInfoRequest();
        editUserInfoRequest.setStatus(-1);

        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            userService.editUserInfo(editUserInfoRequest, requestMetaInfo);
        });

        assertEquals("Parameters error:Invalid payload (status should be between 0 and 3)", expectedException.getErrorMsg());
    }
}

