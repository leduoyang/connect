package com.connect.core.service.user.iml;

import com.connect.api.verification.request.QueryEmailVerificationRequest;
import com.connect.api.verification.request.VerifyEmailVerificationRequest;
import com.connect.common.enums.CodeStatus;
import com.connect.common.util.EmailUtil;
import com.connect.core.service.user.IUserVerificationService;
import com.connect.data.entity.User;
import com.connect.data.entity.UserVerification;
import com.connect.data.repository.IUserRepository;
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

    public UserVerificationServiceImpl(IUserVerificationRepository userVerificationRepository) {
        this.userVerificationRepository = userVerificationRepository;
    }

    @Override
    public boolean sendCodeByEmail(QueryEmailVerificationRequest request) {
        String targetEmail = request.getEmail();
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

    private String generateRandomCode() {
        return UUID.randomUUID().toString().substring(0, 5);
    }
}
