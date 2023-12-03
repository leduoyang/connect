package com.connect.api.verification.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class QueryEmailVerificationResponse {
    private boolean success = false;
}
