package com.connect.api.user.response;

import com.connect.api.user.vo.UserVo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Accessors(chain = true)
@Data
public class QueryUserResponse {
    private List<UserVo> items;

    private int total = 0;
}
