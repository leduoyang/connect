package com.connect.web.controller.follow;

import com.connect.api.common.APIResponse;
import com.connect.api.follow.IFollowApi;
import com.connect.api.follow.dto.FollowDto;
import com.connect.api.follow.dto.UnFollowDto;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.follow.IFollowService;
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

    public FollowController(IFollowService followService) {
        this.followService = followService;
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
    public APIResponse<Map<String, Boolean>> followExisting(String followingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean existed = followService.followExisting(
                authentication.getName(),
                followingId,
                true
        );

        Map<String, Boolean> result = new HashMap<>();
        result.put("existed", existed);
        return APIResponse.getOKJsonResult(result);
    }
}
