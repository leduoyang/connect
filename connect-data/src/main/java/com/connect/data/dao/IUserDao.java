package com.connect.data.dao;

import com.connect.data.dto.UserDto;
import com.connect.data.entity.Profile;
import com.connect.data.entity.User;
import com.connect.data.entity.UserVerification;

import java.util.List;

public interface IUserDao {
    boolean authenticateRootUser(String userId, String password);

    User signIn(String username);

    int signUp(User user);

    int editUser(User user);

    int editUserProfile(Profile profile);

    int incrementViews(Long userId, int version);

    int refreshFollowers(Long userId, int version, int followers);

    int refreshFollowings(Long userId, int version, int followings);

    int deleteUser(Long userId);

    List<UserDto> queryUser(String keyword, Long requesterId);

    User internalQueryUserByUserId(Long userId);

    User internalQueryUserByUsername(String username);

    UserDto queryUserByUsername(String username, Long requesterId);

    boolean userExisting(Long userId);

    boolean userExistingWithUsernameAndEmail(String username, String email);

    boolean isEmailExisting(String email);
}
