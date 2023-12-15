package com.connect.api.follow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class UnFollowDto {
    private String followerId;

    private String followingId;

    private final boolean isActive = false;
}
