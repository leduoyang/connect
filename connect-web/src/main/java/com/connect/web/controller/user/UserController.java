package com.connect.web.controller.user;

import com.connect.api.comment.dto.QueryCommentDto;
import com.connect.api.comment.dto.QueryCommentResponseDto;
import com.connect.api.common.APIResponse;
import com.connect.api.common.RequestMetaInfo;
import com.connect.api.post.dto.QueryPostDto;
import com.connect.api.post.dto.QueryPostResponseDto;
import com.connect.api.project.dto.QueryProjectDto;
import com.connect.api.project.dto.QueryProjectResponseDto;
import com.connect.api.user.IUserApi;
import com.connect.api.user.dto.UserDto;
import com.connect.api.user.request.*;
import com.connect.api.user.response.QueryFollowingListResponse;
import com.connect.api.user.response.QueryStarListResponse;
import com.connect.api.user.response.QueryFollowerListResponse;
import com.connect.api.user.response.QueryUserResponse;
import com.connect.common.enums.RedisPrefix;
import com.connect.common.enums.StarTargetType;
import com.connect.common.enums.UserRole;
import com.connect.common.enums.UserStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.common.util.RedisUtil;
import com.connect.core.service.star.IStarService;
import com.connect.core.service.user.IUserService;
import com.connect.core.service.user.IUserVerificationService;
import com.connect.web.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class UserController implements IUserApi {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final RedisUtil redisUtil;

    private final IUserService userService;

    private final IUserVerificationService userVerificationService;

    public UserController(
            IUserService userService,
            IUserVerificationService userVerificationService,
            RedisUtil redisUtil,
            IStarService starService
    ) {
        this.userService = userService;
        this.userVerificationService = userVerificationService;
        this.redisUtil = redisUtil;
    }

    @Override
    public APIResponse signIn(@RequestBody SignInRequest request) {
        UserDto userDto = userService.signIn(request);
        if (userDto == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Sign in failed where username or password is incorrect"
            );
        }

        String token = jwtTokenUtil.generateToken(
                request.getUserId(),
                UserRole.getRole(userDto.getRole())
        );
        return APIResponse.getOKJsonResult(token);
    }

    @Override
    public APIResponse<Void> signUp(
            @RequestBody SignUpRequest request
    ) {
        if (!userVerificationService.checkEmailComplete(request.getEmail())) {
            throw new ConnectDataException(
                    ConnectErrorCode.USER_VERIFICATION_NOT_EXISTED_EXCEPTION,
                    String.format("Target email %s has not been verified.", request.getEmail())
            );
        }

        if (!request.getUid().equals(redisUtil.getValue(RedisPrefix.USER_SIGNUP_EMAIL, request.getEmail()))) {
            throw new ConnectDataException(
                    ConnectErrorCode.USER_VERIFICATION_NOT_EXISTED_EXCEPTION,
                    String.format("Verified UID of email %s does not align with server.", request.getEmail())
            );
        }

        userService.signUp(request);
        redisUtil.deleteKey(RedisPrefix.USER_SIGNUP_EMAIL, request.getEmail());
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> editPersonalInfo(
            @RequestBody EditUserRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(authentication.getName())
                .setDetails(authentication.getDetails());

        userService.editUser(request, requestMetaInfo);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> editProfile(
            @PathVariable String userId,
            @RequestBody EditProfileRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(authentication.getName())
                .setDetails(authentication.getDetails());

        if (!authentication.getName().equals(userId) &&
                !authentication.getAuthorities()
                        .stream()
                        .findFirst()
                        .equals(UserRole.getRole(UserRole.ADMIN.getCode()))) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                    "unauthorized request for editing target user " + userId
            );
        }

        userService.editUserProfile(request, requestMetaInfo);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> deleteUser(
            @PathVariable String userId
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getName().equals(userId) &&
                !authentication.getAuthorities()
                        .stream()
                        .findFirst()
                        .equals(UserRole.getRole(UserRole.ADMIN.getCode()))) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                    "unauthorized request for deleting target user " + userId
            );
        }

        userService.deleteUser(userId);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> uploadProfileImage(@RequestParam("file") MultipartFile profileImage) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(authentication.getName())
                .setDetails(authentication.getDetails());

        userService.editProfileImage(profileImage, requestMetaInfo);

        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<QueryUserResponse> queryPersonalInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.internalQueryUserByUserId(authentication.getName());
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto);

        QueryUserResponse response = new QueryUserResponse()
                .setItems(userDtoList)
                .setTotal(userDtoList.size());

        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryUserResponse> queryUser(String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(authentication.getName())
                .setDetails(authentication.getDetails());

        UserDto userDto = userService.queryUserByUserId(userId, requestMetaInfo);
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto);

        QueryUserResponse response = new QueryUserResponse()
                .setItems(userDtoList)
                .setTotal(userDtoList.size());

        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryUserResponse> queryUserWithFilter(
            @Validated QueryUserRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(authentication.getName())
                .setDetails(authentication.getDetails());

        List<UserDto> userDtoList = userService.queryUser(request, requestMetaInfo);

        QueryUserResponse response = new QueryUserResponse()
                .setItems(userDtoList)
                .setTotal(userDtoList.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryStarListResponse> queryPersonalStarList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(authentication.getName())
                .setDetails(authentication.getDetails());

        List<QueryProjectResponseDto> projectDtoList =
                userService.queryUserStarList(StarTargetType.PROJECT, requestMetaInfo, QueryProjectResponseDto.class);
        List<QueryPostResponseDto> postDtoList =
                userService.queryUserStarList(StarTargetType.POST, requestMetaInfo, QueryPostResponseDto.class);
        List<QueryCommentResponseDto> commentDtoList =
                userService.queryUserStarList(StarTargetType.COMMENT, requestMetaInfo, QueryCommentResponseDto.class);

        QueryStarListResponse response = new QueryStarListResponse()
                .setProjects(projectDtoList)
                .setTotalProjects(projectDtoList.size())
                .setPosts(postDtoList)
                .setTotalPosts(postDtoList.size())
                .setComments(commentDtoList)
                .setTotalComments(commentDtoList.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryFollowerListResponse> queryPersonalFollowerList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<UserDto> userDtoList = userService.queryFollowerList(authentication.getName());

        QueryFollowerListResponse response = new QueryFollowerListResponse()
                .setUsers(userDtoList)
                .setTotal(userDtoList.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryFollowingListResponse> queryPersonalFollowingList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<UserDto> userDtoList = userService.queryFollowingList(authentication.getName());

        QueryFollowingListResponse response = new QueryFollowingListResponse()
                .setUsers(userDtoList)
                .setTotal(userDtoList.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryFollowerListResponse> queryPersonalPendingList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.internalQueryUserByUserId(authentication.getName());
        if (userDto.getStatus() != UserStatus.SEMI.getCode()) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                    "follower pending list is not available for target user: " + authentication.getName()
            );
        }

        List<UserDto> userDtoList = userService.queryPendingList(authentication.getName());

        QueryFollowerListResponse response = new QueryFollowerListResponse()
                .setUsers(userDtoList)
                .setTotal(userDtoList.size());
        return APIResponse.getOKJsonResult(response);
    }
}
