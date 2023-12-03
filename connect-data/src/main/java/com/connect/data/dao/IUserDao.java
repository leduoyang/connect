package com.connect.data.dao;

import com.connect.data.entity.User;
import com.connect.data.entity.UserVerification;

import java.util.List;

public interface IUserDao {
    boolean authenticateRootUser(String userId, String password);

    User queryUserByUserId(String userId);

    List<User> queryUser(String keyword);

    int createUser(User user);

    int updateUser(User user);

    int deleteUser(String userId);

    boolean userExisting(String userId);

    boolean userExistingWithEmail(String userId, String email);
}
