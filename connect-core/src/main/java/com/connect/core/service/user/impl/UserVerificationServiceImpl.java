package com.connect.core.service.user.impl;

import com.connect.api.verification.request.QueryEmailVerificationRequest;
import com.connect.api.verification.request.VerifyEmailVerificationRequest;
import com.connect.common.enums.CodeStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.common.util.EmailUtil;
import com.connect.core.service.user.IUserService;
import com.connect.core.service.user.IUserVerificationService;
import com.connect.data.entity.UserVerification;
import com.connect.data.repository.IUserVerificationRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@Service
public class UserVerificationServiceImpl implements IUserVerificationService {
    @Autowired
    private EmailUtil emailUtil;

    private IUserVerificationRepository userVerificationRepository;

    private IUserService userService;

    public UserVerificationServiceImpl(IUserVerificationRepository userVerificationRepository, IUserService userService) {
        this.userVerificationRepository = userVerificationRepository;
        this.userService = userService;
    }

    @Override
    public boolean sendCodeByEmail(QueryEmailVerificationRequest request) {
        String targetEmail = request.getEmail();
        if(userService.isEmailExisting(targetEmail)) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    String.format("email %s has been registered!", targetEmail)
            );
        }
        String code = generateRandomCode();

        UserVerification userVerification = new UserVerification()
                .setEmail(targetEmail)
                .setCode(code)
                .setStatus(CodeStatus.PENDING.getCode());
        userVerificationRepository.createUserVerification(userVerification);

        emailUtil.sendVerificationEmail(targetEmail, code);
        return true;
    }

    @Override
    public boolean verifyCodeByEmail(VerifyEmailVerificationRequest request) {
        String targetEmail = request.getEmail();
        String targetCode = request.getCode();

        UserVerification userVerification = new UserVerification()
                .setEmail(targetEmail)
                .setCode(targetCode)
                .setStatus(CodeStatus.PENDING.getCode());
        userVerificationRepository.verifyCode(userVerification);
        return true;
    }

    @Override
    public boolean checkEmailComplete(String email) {
        return userVerificationRepository.checkEmailComplete(email);
    }

    private String generateRandomCode() {
        return UUID.randomUUID().toString().substring(0, 5);
    }
}
