package com.connect.api.verification;

import com.connect.api.common.APIResponse;
import com.connect.api.verification.request.QueryEmailVerificationRequest;
import com.connect.api.verification.request.VerifyEmailVerificationRequest;
import com.connect.api.verification.response.QueryEmailVerificationResponse;
import com.connect.api.verification.response.VerifyEmailVerificationResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/connect/v1")
public interface IUserVerificationApi {
    @GetMapping(value = "/public/verification/email")
    APIResponse<QueryEmailVerificationResponse> queryEmailVerification(
            @Validated QueryEmailVerificationRequest request
    );

    @PostMapping(value = "/public/verification/email")
    APIResponse<VerifyEmailVerificationResponse> verifyEmailVerification(
            @Validated @RequestBody VerifyEmailVerificationRequest request
    );
}
