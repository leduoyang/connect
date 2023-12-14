package com.connect.data.dao;

import com.connect.data.entity.Profile;
import com.connect.data.entity.User;
import com.connect.data.entity.UserVerification;

import java.util.List;

public interface IUserDao {
    boolean authenticateRootUser(String userId, String password);

    User signIn(String userId);

    int signUp(User user);

    int editUser(User user);

    int editUserProfile(Profile profile);

    int incrementViews(long id, int version);

    int deleteUser(String userId);

    List<User> queryUser(String keyword);

    User queryUserByUserId(String userId);

    boolean userExisting(String userId);

    boolean userExistingWithEmail(String userId, String email);
}
