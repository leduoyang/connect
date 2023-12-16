package com.connect.api.follow.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class FollowDto {
    private String followerId;

    private String followingId;
}
