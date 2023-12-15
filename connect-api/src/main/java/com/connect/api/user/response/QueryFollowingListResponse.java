package com.connect.api.user.response;

import com.connect.api.user.dto.UserDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class QueryFollowingListResponse {
    private List<UserDto> users;

    private int total = 0;
}
