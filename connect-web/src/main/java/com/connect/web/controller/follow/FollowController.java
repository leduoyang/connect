package com.connect.web.controller.follow;

import com.connect.api.common.APIResponse;
import com.connect.api.follow.IFollowApi;
import com.connect.api.follow.dto.FollowDto;
import com.connect.api.follow.dto.UnFollowDto;
import com.connect.api.user.dto.UserDto;
import com.connect.common.enums.FollowStatus;
import com.connect.common.enums.UserStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.follow.IFollowService;
import com.connect.core.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class FollowController implements IFollowApi {
    private final IFollowService followService;

    private final IUserService userService;

    public FollowController(IFollowService followService, IUserService userService) {
        this.followService = followService;
        this.userService = userService;
    }

    @Override
    public APIResponse<Void> follow(String followingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals(followingId)) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    String.format("nested follow found")
            );
        }

        FollowDto followDto = new FollowDto()
                .setFollowerId(authentication.getName())
                .setFollowingId(followingId);

        followService.follow(followDto);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> unfollow(String followingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals(followingId)) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    String.format("nested unfollow found")
            );
        }

        UnFollowDto unFollowDto = new UnFollowDto()
                .setFollowerId(authentication.getName())
                .setFollowingId(followingId);

        followService.unfollow(unFollowDto);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Map<String, Boolean>> isFollowing(String followingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean existed = followService.isFollowing(
                authentication.getName(),
                followingId,
                FollowStatus.APPROVED
        );

        Map<String, Boolean> result = new HashMap<>();
        result.put("existed", existed);
        return APIResponse.getOKJsonResult(result);
    }

    @Override
    public APIResponse<Void> approve(String followerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.queryUserByUserId(authentication.getName());
        if (userDto.getStatus() != UserStatus.SEMI.getCode()) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                    "follower pending list is not available for target user: " + authentication.getName()
            );
        }

        followService.approve(authentication.getName(), followerId);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> reject(String followerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.queryUserByUserId(authentication.getName());
        if (userDto.getStatus() != UserStatus.SEMI.getCode()) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                    "follower pending list is not available for target user: " + authentication.getName()
            );
        }

        followService.reject(authentication.getName(), followerId);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> remove(String followerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        followService.remove(authentication.getName(), followerId);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> approveAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.queryUserByUserId(authentication.getName());
        if (userDto.getStatus() != UserStatus.SEMI.getCode()) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                    "follower pending list is not available for target user: " + authentication.getName()
            );
        }

        followService.approveAll(authentication.getName());
        return APIResponse.getOKJsonResult(null);
    }
}
