package com.connect.data.repository.impl;

import com.connect.common.enums.CodeStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.data.dao.IUserVerificationDao;
import com.connect.data.entity.UserVerification;
import com.connect.data.repository.IUserVerificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class UserVerificationRepositoryImpl implements IUserVerificationRepository {

    @Autowired
    IUserVerificationDao userVerificationDao;

    public UserVerificationRepositoryImpl(IUserVerificationDao userVerificationDao) {
        this.userVerificationDao = userVerificationDao;
    }

    @Override
    public void verifyCode(UserVerification userVerification) {
        boolean existed = userVerificationDao.verifyCode(userVerification);
        if (!existed) {
            throw new ConnectDataException(
                    ConnectErrorCode.USER_VERIFICATION_NOT_EXISTED_EXCEPTION,
                    String.format("Target userId %s, code %s, and status %s does not exist.",
                            userVerification.getEmail(),
                            userVerification.getCode(),
                            CodeStatus.getStatus(userVerification.getStatus())
                    )
            );
        }

        int affected = userVerificationDao.completeCodeByEmail(
                userVerification.getEmail(),
                userVerification.getCode()
        );
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.USER_CREATE_EXCEPTION);
        }
    }

    @Override
    public void createUserVerification(UserVerification userVerification) {
        userVerificationDao.expireCodeByEmail(userVerification.getEmail());
        int affected = userVerificationDao.createUserVerification(userVerification);
        if (affected <= 0) {
            throw new ConnectDataException(
                    ConnectErrorCode.USER_VERIFICATION_CREATE_EXCEPTION,
                    "create email verification code failed"
            );
        }
    }

    @Override
    public boolean checkEmailComplete(String email) {
        return userVerificationDao.checkEmailComplete(email);
    }
}
