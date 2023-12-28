package com.connect.data.repository;

import com.connect.data.entity.Profile;
import com.connect.data.entity.User;
import com.connect.data.param.QueryUserParam;

import java.util.List;

public interface IUserRepository {
    User signIn(String username);

    void signUp(User user);

    void editUser(long requesterId, User user);

    void editUserProfile(long requesterId, Profile profile);

    void incrementViews(long userId, int version);

    void refreshFollowers(long userId, int version, int followers);

    void refreshFollowings(long userId, int version, int followings);

    void deleteUser(long userId);

    List<User> queryUser(QueryUserParam param, long requesterId);

    User queryUserByUserId(long userId, long requesterId);

    User internalQueryUserByUserId(long userId);

    User internalQueryUserByUsername(String username);

    boolean isEmailExisting(String email);
}
