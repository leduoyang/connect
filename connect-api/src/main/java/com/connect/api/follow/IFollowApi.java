package com.connect.api.follow;

import com.connect.api.common.APIResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RequestMapping(value = "/api/connect/v1")
public interface IFollowApi {
    @PostMapping(value = "/follow/{following}")
    APIResponse<Void> follow(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @NotNull @PathVariable String following
    );

    @DeleteMapping(value = "/follow/{following}")
    APIResponse<Void> unfollow(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @NotNull @PathVariable String following
    );

    @GetMapping(value = "/follow/isFollowing/{following}")
    APIResponse<Map<String, Boolean>> isFollowing(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @NotNull @PathVariable String following
    );

    @DeleteMapping(value = "/follow/remove/{follower}")
    APIResponse<Void> remove(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @NotNull @PathVariable String follower
    );

    @PostMapping(value = "/follow/approve/{follower}")
    APIResponse<Void> approve(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @NotNull @PathVariable String follower
    );

    @PostMapping(value = "/follow/reject/{follower}")
    APIResponse<Void> reject(
            @RequestHeader(name = "Authorization") String authorizationHeader,
            @NotNull @PathVariable String follower
    );

    @PostMapping(value = "/follow/approveAll")
    APIResponse<Void> approveAll(@RequestHeader(name = "Authorization") String authorizationHeader);
}
