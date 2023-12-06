package com.connect.api.post.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class DeletePostDto {
    private Long id;

    private String userId;
}
