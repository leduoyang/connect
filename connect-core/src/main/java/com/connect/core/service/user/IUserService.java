package com.connect.core.service.user;

import com.connect.api.root.request.RootLoginRequest;
import com.connect.api.user.dto.UserDto;
import com.connect.api.user.request.CreateUserRequest;
import com.connect.api.user.request.QueryUserRequest;
import com.connect.api.user.request.UpdateUserRequest;

import java.util.List;

public interface IUserService {
    boolean authenticateRootUser(RootLoginRequest request);

    UserDto queryUserByUserId(String userId);

    List<UserDto> queryUser(QueryUserRequest request);

    void createUser(CreateUserRequest request);

    void updateUser(String userId, UpdateUserRequest request);

    void deleteUser(String userId);
}
