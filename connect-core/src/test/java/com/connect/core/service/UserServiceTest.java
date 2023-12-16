package com.connect.core.service;

import com.connect.api.user.request.EditUserRequest;
import com.connect.api.user.request.SignUpRequest;
import com.connect.common.enums.UserStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.core.service.user.iml.UserServiceImpl;
import com.connect.data.entity.User;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = UserServiceImpl.class)
public class UserServiceTest {
    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private IUserRepository userRepository;

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
    public void test_update_post_without_status_should_avoid_default_value() {
        EditUserRequest editUserRequest = new EditUserRequest();

        userService.editUser("userId", editUserRequest);

        verify(userRepository, times(1)).editUser(any(), postCaptor.capture());
        User capturedUser = postCaptor.getValue();
        assertNull(capturedUser.getStatus());
    }

    @Test
    public void test_update_post_with_valid_status_should_edit_with_target_value() {
        int targetStatus = UserStatus.PRIVATE.getCode();
        EditUserRequest editUserRequest = new EditUserRequest();
        editUserRequest.setStatus(targetStatus);

        userService.editUser("userId", editUserRequest);

        verify(userRepository, times(1)).editUser(any(), postCaptor.capture());
        User capturedUser = postCaptor.getValue();
        assertEquals(targetStatus, capturedUser.getStatus());
    }

    @Test
    public void test_update_post_with_invalid_status_should_raise_exception() {
        EditUserRequest editUserRequest = new EditUserRequest();
        editUserRequest.setStatus(-1);

        ConnectDataException expectedException = assertThrows(ConnectDataException.class, () -> {
            userService.editUser("userId", editUserRequest);
        });

        assertEquals("Parameters error:Invalid payload (status should be between 0 and 3)", expectedException.getErrorMsg());
    }
}

