package com.connect.data.param;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class QueryUserParam {
    private String keyword;
}
