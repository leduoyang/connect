package com.connect.api.user.request;

import com.connect.api.post.dto.PostDto;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Accessors(chain = true)
@Data
public class CreateUserRequest {
    @NotNull
    @NotBlank
    private String userId;

    @NotNull
    @NotBlank
    private String password;

    @Size(max = 3)
    private int status = 1;

    @Size(max = 3)
    private int role = 1;

    private String description;

    @NotBlank
    private String email;

    @NotBlank
    private String phone;

    @NotNull
    @NotBlank
    private String uid;
}
