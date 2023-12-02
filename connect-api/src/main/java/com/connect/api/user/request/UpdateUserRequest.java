package com.connect.api.user.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Accessors(chain = true)
@Data
public class UpdateUserRequest {
    @NotNull
    @NotBlank
    private String userId;

    @NotNull
    @NotBlank
    private String password;

    @Size(max = 3)
    private int status;

    @Size(max = 3)
    private int role;

    @Size(max = 200)
    private String description;

    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    private String profileImage;
}
