package com.connect.web.controller.user;

import com.connect.api.common.APIResponse;
import com.connect.api.user.IUserApi;
import com.connect.api.user.dto.UserDto;
import com.connect.api.user.request.*;
import com.connect.api.user.response.QueryUserResponse;
import com.connect.common.enums.RedisPrefix;
import com.connect.common.enums.UserRole;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.common.util.RedisUtil;
import com.connect.core.service.user.IUserService;
import com.connect.core.service.user.IUserVerificationService;
import com.connect.web.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public UserController(IUserService userService, IUserVerificationService userVerificationService, RedisUtil redisUtil) {
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

        userService.editUser(authentication.getName(), request);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> editProfile(
            @PathVariable String userId,
            @RequestBody EditProfileRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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

        userService.editUserProfile(authentication.getName(), request);
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
    public APIResponse<QueryUserResponse> queryUser(String userId) {
        UserDto userDto = userService.queryUserByUserId(userId);
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
        List<UserDto> userDtoList = userService.queryUser(request);

        QueryUserResponse response = new QueryUserResponse()
                .setItems(userDtoList)
                .setTotal(userDtoList.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryUserResponse> queryPersonalInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDto userDto = userService.queryUserByUserId(authentication.getName());
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto);

        QueryUserResponse response = new QueryUserResponse()
                .setItems(userDtoList)
                .setTotal(userDtoList.size());

        return APIResponse.getOKJsonResult(response);
    }
}
