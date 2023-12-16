package com.connect.api.verification.request;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class QueryEmailVerificationRequest {
    @Email(message = "email should be in valid format")
    private String email;
}
