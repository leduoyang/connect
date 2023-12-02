package com.connect.data.repository;

import com.connect.data.entity.User;
import com.connect.data.entity.UserVerification;
import com.connect.data.param.QueryUserParam;

import java.util.List;

public interface IUserVerificationRepository {
    void createUserVerification(UserVerification userVerification);

    void verifyCode(UserVerification userVerification);
}
