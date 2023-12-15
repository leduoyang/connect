package com.connect.core.service.user;

import com.connect.api.root.request.RootLoginRequest;
import com.connect.api.user.dto.UserDto;
import com.connect.api.user.request.*;
import com.connect.common.enums.StarTargetType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {
    boolean authenticateRootUser(RootLoginRequest request);

    UserDto signIn(SignInRequest request);

    void signUp(SignUpRequest request);

    void editUser(String userId, EditUserRequest request);

    void editUserProfile(String userId, EditProfileRequest request);

    void editProfileImage(String userId, MultipartFile image);

    void deleteUser(String userId);

    List<UserDto> queryUser(QueryUserRequest request);

    UserDto queryUserByUserId(String userId);

    <T> List<T> queryUserStarList(String userId, StarTargetType targetType, Class<T> returnClass);

    List<UserDto> queryFollowerList(String userId);

    List<UserDto> queryFollowingList(String userId);
}
