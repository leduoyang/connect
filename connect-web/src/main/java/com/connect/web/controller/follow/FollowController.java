package com.connect.web.controller.follow;

import com.connect.api.common.APIResponse;
import com.connect.api.common.RequestMetaInfo;
import com.connect.api.follow.IFollowApi;
import com.connect.common.enums.UserStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.follow.IFollowService;
import com.connect.core.service.user.IUserService;
import com.connect.data.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
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
    public APIResponse<Void> follow(String authorizationHeader, String following) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        followService.follow(following, requestMetaInfo);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> unfollow(String authorizationHeader, String following) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        followService.unfollow(following, requestMetaInfo);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Map<String, Boolean>> isFollowing(String authorizationHeader, String following) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        boolean existed = followService.isFollowing(following, requestMetaInfo);
        Map<String, Boolean> result = new HashMap<>();
        result.put("existed", existed);
        return APIResponse.getOKJsonResult(result);
    }

    @Override
    public APIResponse<Void> approve(String authorizationHeader, String follower) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        UserDto userDto = userService.internalQueryUserByUserId(Long.parseLong(authentication.getName()));
        if (userDto.getStatus() != UserStatus.SEMI.getCode()) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                    "follower pending list is not available for target user: " + Long.parseLong(authentication.getName())
            );
        }

        followService.approve(follower, requestMetaInfo);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> reject(String authorizationHeader, String follower) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        UserDto userDto = userService.internalQueryUserByUserId(Long.parseLong(authentication.getName()));
        if (userDto.getStatus() != UserStatus.SEMI.getCode()) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                    "follower pending list is not available for target user: " + Long.parseLong(authentication.getName())
            );
        }

        followService.reject(follower, requestMetaInfo);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> remove(String authorizationHeader, String follower) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        followService.remove(follower, requestMetaInfo);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> approveAll(String authorizationHeader) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        UserDto userDto = userService.internalQueryUserByUserId(requestMetaInfo.getUserId());
        if (userDto.getStatus() != UserStatus.SEMI.getCode()) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                    "follower pending list is not available for target user: " + Long.parseLong(authentication.getName())
            );
        }

        List<Long> pendingList = followService.queryPendingIdList(requestMetaInfo);
        if (pendingList == null || pendingList.size() == 0) {
            throw new ConnectDataException(
                    ConnectErrorCode.FOLLOW_NOT_EXISTED_EXCEPTION,
                    String.format("nothing to approve for user %s", Long.parseLong(authentication.getName()))
            );
        }

        followService.approveAll(requestMetaInfo);
        return APIResponse.getOKJsonResult(null);
    }
}
