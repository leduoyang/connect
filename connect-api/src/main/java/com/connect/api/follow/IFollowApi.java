package com.connect.api.follow;

import com.connect.api.common.APIResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RequestMapping(value = "/api/connect/v1")
public interface IFollowApi {
    @PostMapping(value = "/follow/{followingId}")
    APIResponse<Void> follow(
            @NotNull @PathVariable String followingId
    );

    @PostMapping(value = "/unfollow/{followingId}")
    APIResponse<Void> unfollow(
            @NotNull @PathVariable String followingId
    );

    @GetMapping(value = "/isFollowing/{followingId}")
    APIResponse<Map<String, Boolean>> isFollowing(
            @NotNull @PathVariable String followingId
    );

    @PostMapping(value = "/follow/request/approve/{followerId}")
    APIResponse<Void> approve(
            @NotNull @PathVariable String followerId
    );

    @PostMapping(value = "/follow/request/reject/{followerId}")
    APIResponse<Void> reject(
            @NotNull @PathVariable String followerId
    );

    @PostMapping(value = "/follow/request/remove/{followerId}")
    APIResponse<Void> remove(
            @NotNull @PathVariable String followerId
    );

    @PostMapping(value = "/follow/request/approveAll")
    APIResponse<Void> approveAll();
}
