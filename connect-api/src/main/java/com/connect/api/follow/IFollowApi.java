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

    @GetMapping(value = "/followExisting/{followingId}")
    APIResponse<Map<String, Boolean>> followExisting(
            @NotNull @PathVariable String followingId
    );
}
