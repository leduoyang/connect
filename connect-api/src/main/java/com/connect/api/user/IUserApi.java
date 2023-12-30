package com.connect.api.user;

import com.connect.api.common.APIResponse;
import com.connect.api.user.request.*;
import com.connect.api.user.response.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping(value = "/api/connect/v1")
public interface IUserApi {
    @GetMapping(value = "/public/user/{username}")
    APIResponse<QueryUserResponse> publicQueryUserByUsername(
            @NotNull @PathVariable String username
    );

    @PostMapping(value = "/public/user/signin")
    APIResponse<SignInResponse> signIn(
            @Validated @RequestBody SignInRequest request
    );

    @PostMapping(value = "/public/user/signup")
    APIResponse<Void> signUp(
            @Validated @RequestBody SignUpRequest request
    );

    @PatchMapping(value = "/user/me/info")
    APIResponse<Void> editPersonalInfo(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated @RequestBody EditUserInfoRequest request
    );

    @PatchMapping(value = "/user/me/profile")
    APIResponse<Void> editProfile(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated @RequestBody EditProfileRequest request
    );

    @DeleteMapping(value = "/user/me")
    APIResponse<Void> deleteUser(
            @RequestHeader(name = "Authorization") String authorizationHeader
    );

    @PostMapping(value = {"/user/upload/profileImage"})
    APIResponse<Void> uploadProfileImage(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated @RequestParam("file") MultipartFile profileImage
    );

    @GetMapping(value = "/user/me")
    APIResponse<QueryUserResponse> queryPersonalInfo(
            @RequestHeader(name = "Authorization") String authorizationHeader
    );

    @GetMapping(value = "/user/me/starList")
    APIResponse<QueryStarListResponse> queryPersonalStarList(
            @RequestHeader(name = "Authorization") String authorizationHeader
    );

    @GetMapping(value = "/user/me/pendingList")
    APIResponse<QueryFollowerListResponse> queryPersonalPendingList(
            @RequestHeader(name = "Authorization") String authorizationHeader
    );

    @GetMapping(value = "/user/me/followerList")
    APIResponse<QueryFollowerListResponse> queryPersonalFollowerList(
            @RequestHeader(name = "Authorization") String authorizationHeader
    );

    @GetMapping(value = "/user/me/followingList")
    APIResponse<QueryFollowingListResponse> queryPersonalFollowingList(
            @RequestHeader(name = "Authorization") String authorizationHeader
    );

    @GetMapping(value = "/user/{username}")
    APIResponse<QueryUserResponse> queryUserByUsername(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @NotNull @PathVariable String username
    );

    @GetMapping(value = "/user")
    APIResponse<QueryUserResponse> queryUserWithFilter(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @Validated QueryUserRequest request
    );
}
