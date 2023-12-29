package com.connect.core.service.user;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.user.vo.UserVo;
import com.connect.api.user.request.*;
import com.connect.common.enums.StarTargetType;
import com.connect.data.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IUserService {
    UserDto signIn(SignInRequest request);

    void signUp(SignUpRequest request);

    void editUserInfo(EditUserInfoRequest request, RequestMetaInfo requestMetaInfo);

    void editUserProfile(EditProfileRequest request, RequestMetaInfo requestMetaInfo);

    void editProfileImage(MultipartFile image, RequestMetaInfo requestMetaInfo);

    void deleteUser(String username);

    List<UserVo> queryUser(QueryUserRequest request, RequestMetaInfo requestMetaInfo);

    UserVo queryUserByUsername(String username, RequestMetaInfo requestMetaInfo);

    UserDto internalQueryUserByUserId(long userId);

    UserDto internalQueryUserByUsername(String username);

    <T> List<T> queryUserStarList(StarTargetType targetType, RequestMetaInfo requestMetaInfo, Class<T> returnClass);

    List<UserVo> queryFollowerList(String username);

    List<UserVo> queryFollowingList(String username);

    List<UserVo> queryPendingList(String username);

    boolean isEmailExisting(String email);
}
