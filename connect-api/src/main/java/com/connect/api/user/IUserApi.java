package com.connect.api.user;

import com.connect.api.common.APIResponse;
import com.connect.api.user.request.CreateUserRequest;
import com.connect.api.user.request.QueryUserRequest;
import com.connect.api.user.request.UpdateUserRequest;
import com.connect.api.user.response.QueryUserResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RequestMapping(value = "/api/connect/v1")
public interface IUserApi {

    @GetMapping(value = "/user/{userId}")
    APIResponse<QueryUserResponse> queryUser(@NotNull @PathVariable String userId);

    @GetMapping(value = "/user")
    APIResponse<QueryUserResponse> queryUserWithFilter(
            @Validated QueryUserRequest request
    );

    @PostMapping(value = "/user")
    APIResponse<Void> createUser(
            @Validated @RequestBody CreateUserRequest request
    );

    @PatchMapping(value = "/user/{userId}")
    APIResponse<Void> updateUser(
            @Validated @NotNull @PathVariable String userId,
            @Validated @RequestBody UpdateUserRequest request
    );

    @DeleteMapping(value = "/user/{userId}")
    APIResponse<Void> deleteUser(
            @Validated @NotNull @PathVariable String userId
    );
}
