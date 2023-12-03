package com.connect.api.user.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@Data
public class SignInRequest {
    @NotNull
    @NotBlank
    private String userId;

    @NotNull
    @NotBlank
    private String password;
}
