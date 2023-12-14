package com.connect.api.star.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class StarRequest {
    @NotNull(message = "targetId can not be null")
    private Long targetId;

    @NotNull(message = "targetType can not be null")
    private Integer targetType;
}
