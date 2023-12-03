package com.connect.api.verification;

import com.connect.api.common.APIResponse;
import com.connect.api.user.request.CreateUserRequest;
import com.connect.api.user.request.QueryUserRequest;
import com.connect.api.user.request.UpdateUserRequest;
import com.connect.api.user.response.QueryUserResponse;
import com.connect.api.verification.request.QueryEmailVerificationRequest;
import com.connect.api.verification.request.VerifyEmailVerificationRequest;
import com.connect.api.verification.response.QueryEmailVerificationResponse;
import com.connect.api.verification.response.VerifyEmailVerificationResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RequestMapping(value = "/api/connect/v1")
public interface IUserVerificationApi {
    @GetMapping(value = "/verification/email")
    APIResponse<QueryEmailVerificationResponse> queryEmailVerification(
            @Validated QueryEmailVerificationRequest request
    );

    @PostMapping(value = "/verification/email")
    APIResponse<VerifyEmailVerificationResponse> verifyEmailVerification(
            @Validated @RequestBody VerifyEmailVerificationRequest request
    );
}
