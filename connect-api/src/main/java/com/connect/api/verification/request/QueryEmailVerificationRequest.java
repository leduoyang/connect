package com.connect.api.verification.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class QueryEmailVerificationRequest {
    private String email;
}
