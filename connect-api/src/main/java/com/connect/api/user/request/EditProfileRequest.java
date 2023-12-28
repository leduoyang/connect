package com.connect.api.user.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

@Accessors(chain = true)
@Data
public class EditProfileRequest {
    @Size(min = 1, max = 20, message = "userId can not be blank and must be at most 20")
    private String username;

    @Min(value = 0, message = "status must be at least 1 (0 - public, 1 - semi, 2 - private)")
    @Max(value = 2, message = "status must be at most 2 (0 - public, 1 - semi, 2 - private)")
    private Integer status;

    @Size(max = 200)
    private String description;

    private Map<String, String> socialLinks;

    private String profileImage;
}
