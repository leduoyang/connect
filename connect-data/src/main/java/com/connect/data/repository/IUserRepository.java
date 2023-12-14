package com.connect.data.repository;

import com.connect.data.entity.Profile;
import com.connect.data.entity.User;
import com.connect.data.param.QueryUserParam;

import java.util.List;

public interface IUserRepository {
    boolean authenticateRootUser(String userId, String password);

    User signIn(String userId);

    void signUp(User user);

    void editUser(String userId, User user);

    void editUserProfile(String userId, Profile profile);

    void incrementViews(long id, int version);

    void deleteUser(String userId);

    List<User> queryUser(QueryUserParam param);

    User queryUserByUserId(String userId);
}
