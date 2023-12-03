package com.connect.data.dao;

import com.connect.data.entity.UserVerification;

public interface IUserVerificationDao {
    int createUserVerification(UserVerification userVerification);

    boolean verifyCode(UserVerification userVerification);

    int expireCodeByEmail(String email);

    int completeCodeByEmail(String email, String code);

    boolean checkEmailComplete(String email);
}
