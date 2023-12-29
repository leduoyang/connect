package com.connect.api.user.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class EditUserInfoRequest {
    @Size(min = 8, max = 20, message = "password must be between 8 to 20")
    private String password;

    @Min(value = 0, message = "status must be at least 1 (0 - public, 1 - semi, 2 - private)")
    @Max(value = 2, message = "status must be at most 2 (0 - public, 1 - semi, 2 - private)")
    private Integer status;

    @Email(message = "email should be in valid format")
    private String email;

    private String phone;
}
