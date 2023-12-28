package com.connect.api.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class RequestMetaInfo {
    private Long userId;

    private Object details;
}
