package com.connect.api.star.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class StarDto {
    private String username;

    private Long targetId;

    private Integer targetType;

    private final boolean isActive = true;
}
