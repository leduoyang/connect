package com.connect.web.controller.verification;

import com.connect.api.common.APIResponse;
import com.connect.api.verification.IUserVerificationApi;
import com.connect.api.verification.request.QueryEmailVerificationRequest;
import com.connect.api.verification.request.VerifyEmailVerificationRequest;
import com.connect.api.verification.response.QueryEmailVerificationResponse;
import com.connect.api.verification.response.VerifyEmailVerificationResponse;
import com.connect.common.enums.RedisPrefix;
import com.connect.common.util.RedisUtil;
import com.connect.core.service.user.IUserVerificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class UserVerificationController implements IUserVerificationApi {
    private final RedisUtil redisUtil;

    private final IUserVerificationService userVerificationService;

    public UserVerificationController(IUserVerificationService userVerificationService, RedisUtil redisUtil) {
        this.userVerificationService = userVerificationService;
        this.redisUtil = redisUtil;
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
        userVerificationService.verifyCodeByEmail(request);

        String uid = UUID.randomUUID().toString();
        redisUtil.setValueWithExpiration(
                RedisPrefix.USER_SIGNUP_EMAIL,
                request.getEmail(),
                uid,
                10,
                TimeUnit.MINUTES
        );

        VerifyEmailVerificationResponse response = new VerifyEmailVerificationResponse().setUid(uid);
        return APIResponse.getOKJsonResult(response);
    }
}
