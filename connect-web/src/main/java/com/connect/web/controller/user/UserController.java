package com.connect.web.controller.user;

import com.connect.api.common.APIResponse;
import com.connect.api.user.IUserApi;
import com.connect.api.user.dto.UserDto;
import com.connect.api.user.request.CreateUserRequest;
import com.connect.api.user.request.QueryUserRequest;
import com.connect.api.user.request.UpdateUserRequest;
import com.connect.api.user.response.QueryUserResponse;
import com.connect.common.enums.RedisPrefix;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.common.util.RedisUtil;
import com.connect.core.service.user.IUserService;
import com.connect.core.service.user.IUserVerificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class UserController implements IUserApi {
    private final RedisUtil redisUtil;

    private final IUserService userService;

    private final IUserVerificationService userVerificationService;

    public UserController(IUserService userService, IUserVerificationService userVerificationService, RedisUtil redisUtil) {
        this.userService = userService;
        this.userVerificationService = userVerificationService;
        this.redisUtil = redisUtil;
    }

    @Override
    public APIResponse<QueryUserResponse> queryPersonalInfo() {
        String userId = ""; // get userInfo from token or session
        UserDto userDto = userService.queryUserByUserId(userId);
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto);

        QueryUserResponse response = new QueryUserResponse()
                .setItems(userDtoList)
                .setTotal(userDtoList.size());

        return APIResponse.getOKJsonResult(response);
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
    public APIResponse<Void> signUp(
            @Validated @RequestBody CreateUserRequest request
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

        userService.createUser(request);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> editPersonalInfo(
            @Validated @RequestBody UpdateUserRequest request
    ) {
        String userId = ""; // get userInfo from token or session
        userService.updateUser(userId, request);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> deleteUser(
            @Validated @NotNull @PathVariable String userId
    ) {
        userService.deleteUser(userId);
        return APIResponse.getOKJsonResult(null);
    }
}
