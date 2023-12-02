package com.connect.api.verification.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class VerifyEmailVerificationResponse {
    private boolean success = false;
}
