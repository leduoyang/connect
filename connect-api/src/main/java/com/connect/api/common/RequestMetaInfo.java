package com.connect.api.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class RequestMetaInfo {
    private String userId;

    private Object details;
}
