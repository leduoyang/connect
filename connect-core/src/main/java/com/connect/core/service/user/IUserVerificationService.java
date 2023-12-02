package com.connect.core.service.user;

import com.connect.api.root.request.RootLoginRequest;
import com.connect.api.user.dto.UserDto;
import com.connect.api.user.request.CreateUserRequest;
import com.connect.api.user.request.QueryUserRequest;
import com.connect.api.user.request.UpdateUserRequest;
import com.connect.api.verification.request.QueryEmailVerificationRequest;
import com.connect.api.verification.request.VerifyEmailVerificationRequest;

import java.util.List;

public interface IUserVerificationService {
    boolean sendCodeByEmail(QueryEmailVerificationRequest request);

    boolean verifyCodeByEmail(VerifyEmailVerificationRequest request);
}
