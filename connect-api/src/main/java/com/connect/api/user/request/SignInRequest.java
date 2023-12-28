package com.connect.api.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class SignInRequest {
    @NotNull(message = "username can not be null")
    @NotBlank(message = "username can not be blank")
    private String username;

    @NotNull(message = "password can not be null")
    @NotBlank(message = "password can not be blank")
    private String password;
}
