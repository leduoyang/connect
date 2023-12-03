package com.connect.api.user.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Accessors(chain = true)
@Data
public class EditProfileRequest {
    @NotNull
    @NotBlank
    private String userId;

    @Size(max = 3)
    private int status;

    @Size(max = 200)
    private String description;

    private String profileImage;
}
