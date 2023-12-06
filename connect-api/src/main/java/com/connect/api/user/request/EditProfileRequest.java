package com.connect.api.user.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class EditProfileRequest {
    @NotNull(message = "userId must not be null")
    @NotBlank(message = "userId must not be blank")
    private String userId;

    @Min(value = 1, message = "Status must be at least 1")
    @Max(value = 3, message = "Status must be at most 3")
    private Integer status;

    @Size(max = 200)
    private String description;

    private String profileImage;
}
