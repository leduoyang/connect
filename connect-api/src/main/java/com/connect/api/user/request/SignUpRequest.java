package com.connect.api.user.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class SignUpRequest {
    @NotNull(message = "userId can not be null")
    @Min(value = 8, message = "userId must be at least 8")
    @Max(value = 20, message = "userId must be at most 20")
    private String userId;

    @NotNull(message = "password can not be null")
    @Min(value = 8, message = "password must be at least 8")
    @Max(value = 20, message = "password must be at most 20")
    private String password;

    @Size(max = 200, message = "description must be at most 200")
    private String description;

    @Email(message = "email should be in valid format")
    private String email;

    private String phone;

    @NotNull(message = "uid code should not be null")
    private String uid;
}
