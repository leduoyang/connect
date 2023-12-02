package com.connect.api.user.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class QueryUserRequest {
    private String keyword;
}
