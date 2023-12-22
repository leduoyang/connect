package com.connect.core.service.user.iml;

import com.connect.api.comment.dto.QueryCommentResponseDto;
import com.connect.api.common.RequestMetaInfo;
import com.connect.api.post.dto.QueryPostResponseDto;
import com.connect.api.project.dto.QueryProjectResponseDto;
import com.connect.api.root.request.RootLoginRequest;
import com.connect.api.user.dto.UserDto;
import com.connect.api.user.request.*;
import com.connect.common.enums.FollowStatus;
import com.connect.common.enums.StarTargetType;
import com.connect.common.enums.UserStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.common.util.ImageUploadUtil;
import com.connect.core.service.comment.ICommentService;
import com.connect.core.service.post.IPostService;
import com.connect.core.service.project.IProjectService;
import com.connect.core.service.user.IUserService;
import com.connect.data.entity.Follow;
import com.connect.data.entity.Profile;
import com.connect.data.entity.User;
import com.connect.data.param.QueryUserParam;
import com.connect.data.repository.IFollowRepository;
import com.connect.data.repository.IStarRepository;
import com.connect.data.repository.IUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ImageUploadUtil imageUploadUtil;

    private IUserRepository userRepository;

    private IStarRepository starRepository;

    private IFollowRepository followRepository;

    private IProjectService projectService;

    private IPostService postService;

    private ICommentService commentService;

    public UserServiceImpl(
            IUserRepository userRepository,
            IStarRepository starRepository,
            IFollowRepository followRepository,
            IProjectService projectService,
            IPostService postService,
            ICommentService commentService
    ) {
        this.userRepository = userRepository;
        this.starRepository = starRepository;
        this.followRepository = followRepository;
        this.projectService = projectService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @Override
    public boolean authenticateRootUser(RootLoginRequest request) {
        if (!userRepository.authenticateRootUser(
                request.getUserId(),
                request.getPassword()
        )) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload for root login"
            );
        }
        return true;
    }

    @Override
    public UserDto signIn(SignInRequest request) {
        User user = userRepository.signIn(
                request.getUserId()
        );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return null;
        }

        return new UserDto()
                .setUserId(user.getUserId())
                .setStatus(user.getStatus())
                .setRole(user.getRole());
    }

    @Override
    public void signUp(SignUpRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        User user = new User()
                .setStatus(UserStatus.PUBLIC.getCode())
                .setUserId(request.getUserId())
                .setPassword(request.getPassword())
                .setDescription(request.getDescription())
                .setEmail(request.getEmail())
                .setPhone(request.getPhone());

        userRepository.signUp(user);
    }

    @Override
    public void editUser(EditUserRequest request, RequestMetaInfo requestMetaInfo) {
        User user = new User()
                .setUserId(request.getUserId())
                .setPassword(request.getPassword())
                .setDescription(request.getDescription())
                .setEmail(request.getEmail())
                .setPhone(request.getPhone());

        if (request.getStatus() != null) {
            if (request.getStatus() < 0 || request.getStatus() > 3) {
                throw new ConnectDataException(
                        ConnectErrorCode.PARAM_EXCEPTION,
                        "Invalid payload (status should be between 0 and 3)"
                );
            }
            user.setStatus(request.getStatus());
        }

        userRepository.editUser(requestMetaInfo.getUserId(), user);

        if(UserStatus.getStatus(user.getStatus()).equals(UserStatus.PUBLIC)) {
            Follow follow = new Follow()
                    .setFollowingId(requestMetaInfo.getUserId())
                    .setStatus(FollowStatus.APPROVED.getCode());
            followRepository.updateFollow(follow);
        }
    }

    @Override
    public void editUserProfile(EditProfileRequest request, RequestMetaInfo requestMetaInfo) {
        Profile profile = new Profile()
                .setUserId(request.getUserId())
                .setDescription(request.getDescription())
                .setProfileImage(request.getProfileImage());

        if (request.getStatus() != null) {
            if (request.getStatus() < 0 || request.getStatus() > 3) {
                throw new ConnectDataException(
                        ConnectErrorCode.PARAM_EXCEPTION,
                        "Invalid payload (status should be between 0 and 3)"
                );
            }
            profile.setStatus(request.getStatus());
        }

        userRepository.editUserProfile(requestMetaInfo.getUserId(), profile);

        if(UserStatus.getStatus(profile.getStatus()).equals(UserStatus.PUBLIC)) {
            Follow follow = new Follow()
                    .setFollowingId(requestMetaInfo.getUserId())
                    .setStatus(FollowStatus.APPROVED.getCode());
            followRepository.updateFollow(follow);
        }
    }

    @Override
    public void editProfileImage(MultipartFile image, RequestMetaInfo requestMetaInfo) {
        User user = userRepository.internalQueryUserByUserId(requestMetaInfo.getUserId());
        String profileImage =
                imageUploadUtil.profileImage(
                        user.getId() + "." + imageUploadUtil.getExtension(image),
                        image
                );

        EditProfileRequest editProfileRequest = new EditProfileRequest()
                .setProfileImage(profileImage);
        editUserProfile(editProfileRequest, requestMetaInfo);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteUser(userId);
    }

    @Override
    public UserDto queryUserByUserId(String userId, RequestMetaInfo requestMetaInfo) {
        User user = userRepository.queryUserByUserId(userId, requestMetaInfo.getUserId());
        userRepository.incrementViews(
                user.getUserId(),
                user.getVersion()
        );

        UserDto userDto = new UserDto()
                .setUserId(user.getUserId())
                .setStatus(user.getStatus())
                .setRole(user.getRole())
                .setDescription(user.getDescription())
                .setProfileImage(user.getProfileImage())
                .setViews(user.getViews())
                .setFollowers(user.getFollowers())
                .setFollowings(user.getFollowings())
                .setDbCreateTime(user.getDbCreateTime())
                .setDbModifyTime(user.getDbModifyTime());
        return userDto;
    }

    @Override
    public List<UserDto> queryUser(QueryUserRequest request, RequestMetaInfo requestMetaInfo) {
        QueryUserParam param = new QueryUserParam().setKeyword(request.getKeyword());
        List<User> userList = userRepository.queryUser(param, requestMetaInfo.getUserId());

        return userList
                .stream()
                .map(x -> new UserDto()
                        .setUserId(x.getUserId())
                        .setStatus(x.getStatus())
                        .setRole(x.getRole())
                        .setDescription(x.getDescription())
                        .setProfileImage(x.getProfileImage())
                        .setViews(x.getViews())
                        .setFollowers(x.getFollowers())
                        .setFollowings(x.getFollowings())
                        .setDbCreateTime(x.getDbCreateTime())
                        .setDbModifyTime(x.getDbModifyTime())
                )
                .collect(Collectors.toList());
    }

    @Override
    public <T> List<T> queryUserStarList(StarTargetType targetType, RequestMetaInfo requestMetaInfo, Class<T> returnClass) {
        List<Integer> idList = starRepository.queryTargetIdList(targetType.getCode(), requestMetaInfo.getUserId());
        switch (Objects.requireNonNull(targetType)) {
            case PROJECT:
                List<QueryProjectResponseDto> projectDtoList =
                        idList.stream().map(
                                x -> projectService.queryProjectById(x, requestMetaInfo)
                        ).collect(Collectors.toList());
                return (List<T>) projectDtoList;
            case POST:
                List<QueryPostResponseDto> postDtoList =
                        idList.stream().map(
                                x -> postService.queryPostById(x, requestMetaInfo)
                        ).collect(Collectors.toList());
                return (List<T>) postDtoList;
            case COMMENT:
                List<QueryCommentResponseDto> commentDtoList = idList.stream().map(
                        x -> commentService.queryCommentById(x, requestMetaInfo)
                ).collect(Collectors.toList());
                return (List<T>) commentDtoList;
            default:
                throw new ConnectDataException(
                        ConnectErrorCode.INTERNAL_SERVER_ERROR,
                        "Invalid payload updating like counts for target entity"
                );
        }
    }

    public UserDto internalQueryUserByUserId(String userId) {
        User user = userRepository.internalQueryUserByUserId(userId);
        userRepository.incrementViews(
                user.getUserId(),
                user.getVersion()
        );

        UserDto userDto = new UserDto()
                .setUserId(user.getUserId())
                .setStatus(user.getStatus())
                .setRole(user.getRole())
                .setDescription(user.getDescription())
                .setProfileImage(user.getProfileImage())
                .setViews(user.getViews())
                .setFollowers(user.getFollowers())
                .setFollowings(user.getFollowings())
                .setDbCreateTime(user.getDbCreateTime())
                .setDbModifyTime(user.getDbModifyTime());
        return userDto;
    }

    @Override
    public List<UserDto> queryFollowerList(String userId) {
        List<String> userIdList = followRepository.queryFollowerIdList(userId);
        return userIdList.stream().map(this::internalQueryUserByUserId).toList();
    }

    @Override
    public List<UserDto> queryFollowingList(String userId) {
        List<String> userIdList = followRepository.queryFollowingIdList(userId);
        return userIdList.stream().map(this::internalQueryUserByUserId).toList();
    }

    @Override
    public List<UserDto> queryPendingList(String userId) {
        List<String> userIdList = followRepository.queryPendingIdList(userId);
        return userIdList.stream().map(this::internalQueryUserByUserId).toList();
    }
}
