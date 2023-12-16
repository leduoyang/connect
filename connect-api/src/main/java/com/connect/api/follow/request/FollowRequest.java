package com.connect.api.follow.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class FollowRequest {
    @NotNull(message = "followerId can not be null")
    @NotBlank(message = "followerId can not be blank")
    private String followerId;

    @NotNull(message = "followingId can not be null")
    @NotBlank(message = "followingId can not be blank")
    private String followingId;
}
