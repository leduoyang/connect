package com.connect.core.service.user;

import com.connect.api.common.RequestMetaInfo;
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

    void editUser(EditUserRequest request, RequestMetaInfo requestMetaInfo);

    void editUserProfile(EditProfileRequest request, RequestMetaInfo requestMetaInfo);

    void editProfileImage(MultipartFile image, RequestMetaInfo requestMetaInfo);

    void deleteUser(String userId);

    List<UserDto> queryUser(QueryUserRequest request, RequestMetaInfo requestMetaInfo);

    UserDto queryUserByUserId(String userId, RequestMetaInfo requestMetaInfo);

    UserDto internalQueryUserByUserId(String userId);

    <T> List<T> queryUserStarList(StarTargetType targetType, RequestMetaInfo requestMetaInfo, Class<T> returnClass);

    List<UserDto> queryFollowerList(String userId);

    List<UserDto> queryFollowingList(String userId);

    List<UserDto> queryPendingList(String userId);
}
