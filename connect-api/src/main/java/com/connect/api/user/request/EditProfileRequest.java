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

    @Size(max = 200)
    private String description;

    private Map<String, String> socialLinks;

    private String profileImage;
}
