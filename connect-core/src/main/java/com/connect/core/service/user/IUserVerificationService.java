package com.connect.core.service.user;

import com.connect.api.verification.request.QueryEmailVerificationRequest;
import com.connect.api.verification.request.VerifyEmailVerificationRequest;

public interface IUserVerificationService {
    boolean sendCodeByEmail(QueryEmailVerificationRequest request);

    boolean verifyCodeByEmail(VerifyEmailVerificationRequest request);

    boolean checkEmailComplete(String email);
}
