package com.connect.api.user.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class SignInResponse {
    private String token;
}
