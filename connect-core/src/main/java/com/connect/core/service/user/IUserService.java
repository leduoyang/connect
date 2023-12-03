package com.connect.core.service.user;

import com.connect.api.root.request.RootLoginRequest;
import com.connect.api.user.dto.UserDto;
import com.connect.api.user.request.*;

import java.util.List;

public interface IUserService {
    boolean authenticateRootUser(RootLoginRequest request);

    UserDto signIn(SignInRequest request);

    void signUp(SignUpRequest request);

    void editUser(String userId, EditUserRequest request);

    void editUserProfile(String userId, EditProfileRequest request);

    void deleteUser(String userId);

    List<UserDto> queryUser(QueryUserRequest request);

    UserDto queryUserByUserId(String userId);
}
