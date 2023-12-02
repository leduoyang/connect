package com.connect.web.controller.user;

import com.connect.api.common.APIResponse;
import com.connect.api.user.IUserApi;
import com.connect.api.user.dto.UserDto;
import com.connect.api.user.request.CreateUserRequest;
import com.connect.api.user.request.QueryUserRequest;
import com.connect.api.user.request.UpdateUserRequest;
import com.connect.api.user.response.QueryUserResponse;
import com.connect.core.service.user.IUserService;
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
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
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
    public APIResponse<Void> createUser(
            @Validated @RequestBody CreateUserRequest request
    ) {
        userService.createUser(request);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> updateUser(
            @Validated @NotNull @PathVariable String userId,
            @Validated @RequestBody UpdateUserRequest request
    ) {
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
