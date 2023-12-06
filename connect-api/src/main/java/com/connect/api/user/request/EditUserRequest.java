package com.connect.api.user.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class EditUserRequest {
    @NotNull
    @NotBlank
    private String userId;

    @NotNull
    @NotBlank
    private String password;

    @Min(value = 1, message = "status must be at least 1")
    @Max(value = 3, message = "status must be at most 3")
    private Integer status;

    @Size(max = 200, message = "description must be at most 200")
    private String description;

    private String email;

    private String phone;

    private String profileImage;
}
