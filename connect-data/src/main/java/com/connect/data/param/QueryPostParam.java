package com.connect.data.param;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class QueryPostParam {
    private String keyword;

    private String tags;
}
