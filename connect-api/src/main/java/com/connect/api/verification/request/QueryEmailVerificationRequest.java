package com.connect.api.verification.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class QueryEmailVerificationRequest {
    @NotNull(message = "email should not be null")
    @Email(message = "email should be in valid format")
    private String email;
}
