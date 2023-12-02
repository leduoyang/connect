package com.connect.data.repository;

import com.connect.data.entity.User;
import com.connect.data.param.QueryUserParam;

import java.util.List;

public interface IUserRepository {
    boolean authenticateRootUser(String userId, String password);

    boolean checkUserExisting(String userId);

    void createUser(User user);

    void deleteUser(String userId);

    List<User> queryUser(QueryUserParam param);

    User queryUserByUserId(String userId);

    void updateUser(User user);
}
