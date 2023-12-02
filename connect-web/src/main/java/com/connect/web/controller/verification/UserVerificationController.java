package com.connect.web.controller.verification;

import com.connect.api.common.APIResponse;
import com.connect.api.user.IUserApi;
import com.connect.api.user.dto.UserDto;
import com.connect.api.user.request.CreateUserRequest;
import com.connect.api.user.request.QueryUserRequest;
import com.connect.api.user.request.UpdateUserRequest;
import com.connect.api.user.response.QueryUserResponse;
import com.connect.api.verification.IUserVerificationApi;
import com.connect.api.verification.request.QueryEmailVerificationRequest;
import com.connect.api.verification.request.VerifyEmailVerificationRequest;
import com.connect.api.verification.response.QueryEmailVerificationResponse;
import com.connect.api.verification.response.VerifyEmailVerificationResponse;
import com.connect.core.service.user.IUserService;
import com.connect.core.service.user.IUserVerificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class UserVerificationController implements IUserVerificationApi {
    private final IUserVerificationService userVerificationService;

    public UserVerificationController(IUserVerificationService userVerificationService) {
        this.userVerificationService = userVerificationService;
    }

    @Override
    public APIResponse<QueryEmailVerificationResponse> queryEmailVerification(
            @Validated QueryEmailVerificationRequest request
    ) {
        boolean isSuccess = userVerificationService.sendCodeByEmail(request);
        QueryEmailVerificationResponse response = new QueryEmailVerificationResponse()
                .setSuccess(isSuccess);

        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<VerifyEmailVerificationResponse> verifyEmailVerification(
            @Validated @RequestBody VerifyEmailVerificationRequest request
    ) {
        boolean isSuccess = userVerificationService.verifyCodeByEmail(request);
        VerifyEmailVerificationResponse response = new VerifyEmailVerificationResponse()
                .setSuccess(isSuccess);

        return APIResponse.getOKJsonResult(response);
    }
}
