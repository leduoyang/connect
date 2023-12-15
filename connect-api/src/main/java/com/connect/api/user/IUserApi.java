package com.connect.api.user;

import com.connect.api.common.APIResponse;
import com.connect.api.user.request.*;
import com.connect.api.user.response.QueryStarListResponse;
import com.connect.api.user.response.QuerySubscribeListResponse;
import com.connect.api.user.response.QueryUserResponse;
import com.connect.api.user.response.SignInResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(value = "/api/connect/v1")
public interface IUserApi {
    @PostMapping(value = {"/user/signin", "/public/user/signin"})
    APIResponse<SignInResponse> signIn(
            @Validated @RequestBody SignInRequest request
    );

    @PostMapping(value = {"/user/signup", "/public/user/signup"})
    APIResponse<Void> signUp(
            @Validated @RequestBody SignUpRequest request
    );

    @PatchMapping(value = "/user/me/edit")
    APIResponse<Void> editPersonalInfo(
            @Validated @RequestBody EditUserRequest request
    );

    @PatchMapping(value = "/user/{userId}")
    APIResponse<Void> editProfile(
            @Validated @NotNull @PathVariable String userId,
            @Validated @RequestBody EditProfileRequest request
    );

    @DeleteMapping(value = "/user/{userId}")
    APIResponse<Void> deleteUser(
            @Validated @NotNull @PathVariable String userId
    );

    @PostMapping(value = {"/user/upload/profileImage"})
    APIResponse<Void> uploadProfileImage(
            @Validated @RequestParam("file") MultipartFile profileImage
    );

    @GetMapping(value = "/users/me")
    APIResponse<QueryUserResponse> queryPersonalInfo();

    @GetMapping(value = "/users/me/starList")
    APIResponse<QueryStarListResponse> queryPersonalStarList();

    @GetMapping(value = "/users/me/subscribeList")
    APIResponse<QuerySubscribeListResponse> queryPersonalSubscribeList();

    @GetMapping(value = "/user/{userId}")
    APIResponse<QueryUserResponse> queryUser(@NotNull @PathVariable String userId);

    @GetMapping(value = "/user")
    APIResponse<QueryUserResponse> queryUserWithFilter(
            @Validated QueryUserRequest request
    );
}
