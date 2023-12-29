package com.connect.web.controller.user;

import com.connect.api.comment.vo.QueryCommentVo;
import com.connect.api.common.APIResponse;
import com.connect.api.common.RequestMetaInfo;
import com.connect.api.post.vo.QueryPostVo;
import com.connect.api.project.vo.QueryProjectVo;
import com.connect.api.user.IUserApi;
import com.connect.api.user.vo.UserVo;
import com.connect.api.user.request.*;
import com.connect.api.user.response.QueryFollowingListResponse;
import com.connect.api.user.response.QueryStarListResponse;
import com.connect.api.user.response.QueryFollowerListResponse;
import com.connect.api.user.response.QueryUserResponse;
import com.connect.common.enums.RedisPrefix;
import com.connect.common.enums.StarTargetType;
import com.connect.common.enums.UserRole;
import com.connect.common.enums.UserStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.common.util.RedisUtil;
import com.connect.core.service.star.IStarService;
import com.connect.core.service.user.IUserService;
import com.connect.core.service.user.IUserVerificationService;
import com.connect.data.dto.UserDto;
import com.connect.web.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class UserController implements IUserApi {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final RedisUtil redisUtil;

    private final IUserService userService;

    private final IUserVerificationService userVerificationService;

    public UserController(
            IUserService userService,
            IUserVerificationService userVerificationService,
            RedisUtil redisUtil,
            IStarService starService
    ) {
        this.userService = userService;
        this.userVerificationService = userVerificationService;
        this.redisUtil = redisUtil;
    }

    @Override
    public APIResponse signIn(@RequestBody SignInRequest request) {
        UserDto userDto = userService.signIn(request);
        if (userDto == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Sign in failed where username or password is incorrect"
            );
        }

        String token = jwtTokenUtil.generateToken(userDto.getUserId());
        return APIResponse.getOKJsonResult(token);
    }

    @Override
    public APIResponse<Void> signUp(
            @RequestBody SignUpRequest request
    ) {
        if (!userVerificationService.checkEmailComplete(request.getEmail())) {
            throw new ConnectDataException(
                    ConnectErrorCode.USER_VERIFICATION_NOT_EXISTED_EXCEPTION,
                    String.format("Target email %s has not been verified.", request.getEmail())
            );
        }

        if (!request.getUid().equals(redisUtil.getValue(RedisPrefix.USER_SIGNUP_EMAIL, request.getEmail()))) {
            throw new ConnectDataException(
                    ConnectErrorCode.USER_VERIFICATION_NOT_EXISTED_EXCEPTION,
                    String.format("Verified UID of email %s does not align with server.", request.getEmail())
            );
        }

        userService.signUp(request);
        redisUtil.deleteKey(RedisPrefix.USER_SIGNUP_EMAIL, request.getEmail());
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> editPersonalInfo(
            @RequestBody EditUserInfoRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        userService.editUserInfo(request, requestMetaInfo);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> editProfile(
            @RequestBody EditProfileRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        userService.editUserProfile(request, requestMetaInfo);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        userService.deleteUser(requestMetaInfo);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> uploadProfileImage(@RequestParam("file") MultipartFile profileImage) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        userService.editProfileImage(profileImage, requestMetaInfo);

        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<QueryUserResponse> queryPersonalInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.internalQueryUserByUserId(Long.parseLong(authentication.getName()));
        List<UserVo> userVoList = new ArrayList<>();
        userVoList.add(new UserVo()
                .setUsername(userDto.getUsername())
                .setStatus(userDto.getStatus())
                .setDescription(userDto.getDescription())
                .setFollowings(userDto.getFollowings())
                .setFollowers(userDto.getFollowers())
                .setProfileImage(userDto.getProfileImage())
                .setViews(userDto.getViews())
        );

        QueryUserResponse response = new QueryUserResponse()
                .setItems(userVoList)
                .setTotal(userVoList.size());

        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryUserResponse> queryUserByUsername(String username) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        UserVo userDto = userService.queryUserByUsername(username, requestMetaInfo);
        List<UserVo> userVoList = new ArrayList<>();
        userVoList.add(userDto);

        QueryUserResponse response = new QueryUserResponse()
                .setItems(userVoList)
                .setTotal(userVoList.size());

        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryUserResponse> queryUserWithFilter(
            @Validated QueryUserRequest request
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());
        List<UserVo> userVoList = userService.queryUser(request, requestMetaInfo);

        QueryUserResponse response = new QueryUserResponse()
                .setItems(userVoList)
                .setTotal(userVoList.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryStarListResponse> queryPersonalStarList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        RequestMetaInfo requestMetaInfo = new RequestMetaInfo()
                .setUserId(Long.parseLong(authentication.getName()))
                .setDetails(authentication.getDetails());

        List<QueryProjectVo> projectDtoList =
                userService.queryUserStarList(StarTargetType.PROJECT, requestMetaInfo, QueryProjectVo.class);
        List<QueryPostVo> postDtoList =
                userService.queryUserStarList(StarTargetType.POST, requestMetaInfo, QueryPostVo.class);
        List<QueryCommentVo> commentDtoList =
                userService.queryUserStarList(StarTargetType.COMMENT, requestMetaInfo, QueryCommentVo.class);

        QueryStarListResponse response = new QueryStarListResponse()
                .setProjects(projectDtoList)
                .setTotalProjects(projectDtoList.size())
                .setPosts(postDtoList)
                .setTotalPosts(postDtoList.size())
                .setComments(commentDtoList)
                .setTotalComments(commentDtoList.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryFollowerListResponse> queryPersonalFollowerList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.internalQueryUserByUserId(Long.parseLong(authentication.getName()));
        List<UserVo> userVoList = userService.queryFollowerList(userDto.getUsername());

        QueryFollowerListResponse response = new QueryFollowerListResponse()
                .setUsers(userVoList)
                .setTotal(userVoList.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryFollowingListResponse> queryPersonalFollowingList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.internalQueryUserByUserId(Long.parseLong(authentication.getName()));
        List<UserVo> userVoList = userService.queryFollowingList(userDto.getUsername());

        QueryFollowingListResponse response = new QueryFollowingListResponse()
                .setUsers(userVoList)
                .setTotal(userVoList.size());
        return APIResponse.getOKJsonResult(response);
    }

    @Override
    public APIResponse<QueryFollowerListResponse> queryPersonalPendingList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.internalQueryUserByUserId(Long.parseLong(authentication.getName()));
        if (userDto.getStatus() != UserStatus.SEMI.getCode()) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                    "follower pending list is not available for target user: " + authentication.getName()
            );
        }

        List<UserVo> userVoList = userService.queryPendingList(userDto.getUsername());

        QueryFollowerListResponse response = new QueryFollowerListResponse()
                .setUsers(userVoList)
                .setTotal(userVoList.size());
        return APIResponse.getOKJsonResult(response);
    }
}
