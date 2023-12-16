package com.connect.api.user.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class SignUpRequest {
    @NotNull(message = "userId can not be null")
    @NotBlank(message = "userId can not be blank")
    @Size(max = 20, message = "userId must be at most 20")
    private String userId;

    @NotNull(message = "password can not be null")
    @Size(min = 8, max = 20, message = "password must be between 8 to 20")
    private String password;

    @Size(max = 200, message = "description must be at most 200")
    private String description;

    @NotNull(message = "email can not be null")
    @Email(message = "email should be in valid format")
    private String email;

    private String phone;

    @NotNull(message = "uid code should not be null")
    private String uid;
}
