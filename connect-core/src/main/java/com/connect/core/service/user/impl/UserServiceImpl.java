package com.connect.core.service.user.impl;

import com.connect.api.comment.vo.QueryCommentVo;
import com.connect.api.common.RequestMetaInfo;
import com.connect.api.experience.vo.ExperienceVo;
import com.connect.api.post.vo.QueryPostVo;
import com.connect.api.project.vo.QueryProjectVo;
import com.connect.api.socialLink.vo.SocialLinkVo;
import com.connect.api.user.request.*;
import com.connect.api.user.vo.UserVo;
import com.connect.common.enums.FollowStatus;
import com.connect.common.enums.StarTargetType;
import com.connect.common.enums.UserStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.common.util.ImageUploadUtil;
import com.connect.core.service.comment.ICommentService;
import com.connect.core.service.experience.IExperienceService;
import com.connect.core.service.post.IPostService;
import com.connect.core.service.project.IProjectService;
import com.connect.core.service.socialLink.ISocialLinkService;
import com.connect.core.service.user.IUserService;
import com.connect.data.dto.UserDto;
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

    private ISocialLinkService socialLinkService;

    private IExperienceService experienceService;

    private IProjectService projectService;

    private IPostService postService;

    private ICommentService commentService;

    public UserServiceImpl(
            IUserRepository userRepository,
            IStarRepository starRepository,
            ISocialLinkService socialLinkService,
            IFollowRepository followRepository,
            IProjectService projectService,
            IPostService postService,
            IExperienceService experienceService,
            ICommentService commentService
    ) {
        this.userRepository = userRepository;
        this.socialLinkService = socialLinkService;
        this.starRepository = starRepository;
        this.followRepository = followRepository;
        this.projectService = projectService;
        this.postService = postService;
        this.commentService = commentService;
        this.experienceService = experienceService;
    }

    @Override
    public UserDto signIn(SignInRequest request) {
        User user = userRepository.signIn(
                request.getUsername()
        );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return null;
        }

        return new UserDto()
                .setUserId(user.getUserId())
                .setStatus(user.getStatus());
    }

    @Override
    public void signUp(SignUpRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        User user = new User()
                .setStatus(UserStatus.PUBLIC.getCode())
                .setUsername(request.getUsername())
                .setPassword(request.getPassword())
                .setDescription(request.getDescription())
                .setEmail(request.getEmail())
                .setPhone(request.getPhone());

        userRepository.signUp(user);
    }

    @Override
    public void editUserInfo(EditUserInfoRequest request, RequestMetaInfo requestMetaInfo) {
        User user = new User()
                .setPassword(request.getPassword())
                .setEmail(request.getEmail())
                .setPhone(request.getPhone());
        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
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

        if (user.getStatus() != null && UserStatus.getStatus(user.getStatus()).equals(UserStatus.PUBLIC)) {
            Follow follow = new Follow()
                    .setFollowingId(requestMetaInfo.getUserId())
                    .setStatus(FollowStatus.APPROVED.getCode());
            followRepository.updateFollow(follow);
        }
    }

    @Override
    public void editUserProfile(EditProfileRequest request, RequestMetaInfo requestMetaInfo) {
        Profile profile = new Profile()
                .setUsername(request.getUsername())
                .setDescription(request.getDescription())
                .setProfileImage(request.getProfileImage());

        userRepository.editUserProfile(requestMetaInfo.getUserId(), profile);

        if (request.getSocialLinks() != null) {
            socialLinkService.updateUserSocialLink(request.getSocialLinks(), requestMetaInfo);
        }
    }

    @Override
    public void editProfileImage(MultipartFile image, RequestMetaInfo requestMetaInfo) {
        User user = userRepository.internalQueryUserByUserId(requestMetaInfo.getUserId());
        String profileImage =
                imageUploadUtil.profileImage(
                        user.getUuid() + "." + imageUploadUtil.getExtension(image),
                        image
                );

        EditProfileRequest editProfileRequest = new EditProfileRequest()
                .setProfileImage(profileImage);
        editUserProfile(editProfileRequest, requestMetaInfo);
    }

    @Override
    public void deleteUser(RequestMetaInfo requestMetaInfo) {
        userRepository.deleteUser(requestMetaInfo.getUserId());
    }

    @Override
    public boolean isEmailExisting(String email) {
        return userRepository.isEmailExisting(email);
    }

    @Override
    public UserVo queryUserByUsername(String username, RequestMetaInfo requestMetaInfo) {
        UserDto userDto;
        if (requestMetaInfo.getIsPublic() != null && requestMetaInfo.getIsPublic()) {
            userDto = internalQueryUserByUsername(username);
            if (UserStatus.PUBLIC.getCode() != userDto.getStatus()) {
                throw new ConnectDataException(
                        ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                        String.format("User %s is not a PUBLIC account", username)
                );
            }
        } else {
            userDto = userRepository.queryUserByUsername(username, requestMetaInfo.getUserId());
        }
        userRepository.incrementViews(
                userDto.getUserId(),
                userDto.getVersion()
        );

        UserVo userVo = new UserVo()
                .setUsername(userDto.getUsername())
                .setDescription(userDto.getDescription())
                .setProfileImage(userDto.getProfileImage())
                .setViews(userDto.getViews())
                .setFollowers(userDto.getFollowers())
                .setFollowings(userDto.getFollowings());

        List<ExperienceVo> experienceVoList = experienceService
                .internalQueryExperienceByUserId(userDto.getUserId())
                .stream()
                .map(x -> new ExperienceVo()
                        .setTitle(x.getTitle())
                        .setCompany(x.getCompany())
                        .setStart(x.getStart())
                        .setUntil(x.getUntil()))
                .collect(Collectors.toList());
        userVo.setExperienceVoList(experienceVoList);

        List<SocialLinkVo> socialLinkVoList = socialLinkService
                .internalQuerySocialLinkByUserId(userDto.getUserId())
                .stream()
                .map(x -> new SocialLinkVo()
                        .setPlatform(x.getPlatform())
                        .setPlatformId(x.getPlatformId()))
                .collect(Collectors.toList());
        userVo.setSocialLinkVoList(socialLinkVoList);
        return userVo;
    }

    @Override
    public UserDto internalQueryUserByUserId(long userId) {
        User user = userRepository.internalQueryUserByUserId(userId);
        return new UserDto()
                .setUserId(user.getUserId())
                .setUsername(user.getUsername())
                .setStatus(user.getStatus())
                .setRole(user.getRole())
                .setDescription(user.getDescription())
                .setProfileImage(user.getProfileImage())
                .setViews(user.getViews())
                .setFollowers(user.getFollowers())
                .setFollowings(user.getFollowings());
    }

    @Override
    public UserDto internalQueryUserByUsername(String username) {
        User user = userRepository.internalQueryUserByUsername(username);
        return new UserDto()
                .setUserId(user.getUserId())
                .setUsername(user.getUsername())
                .setStatus(user.getStatus())
                .setRole(user.getRole())
                .setDescription(user.getDescription())
                .setProfileImage(user.getProfileImage())
                .setViews(user.getViews())
                .setVersion(user.getVersion())
                .setFollowers(user.getFollowers())
                .setFollowings(user.getFollowings());
    }

    @Override
    public List<UserVo> queryUser(QueryUserRequest request, RequestMetaInfo requestMetaInfo) {
        QueryUserParam param = new QueryUserParam().setKeyword(request.getKeyword());
        List<UserDto> userList = userRepository.queryUser(param, requestMetaInfo.getUserId());

        return userList
                .stream()
                .map(x -> new UserVo()
                        .setDescription(x.getDescription())
                        .setProfileImage(x.getProfileImage())
                        .setViews(x.getViews())
                        .setFollowers(x.getFollowers())
                        .setFollowings(x.getFollowings())
                )
                .collect(Collectors.toList());
    }

    @Override
    public <T> List<T> queryUserStarList(StarTargetType targetType, RequestMetaInfo requestMetaInfo, Class<T> returnClass) {
        List<Integer> idList = starRepository.queryTargetIdList(targetType.getCode(), requestMetaInfo.getUserId());
        switch (Objects.requireNonNull(targetType)) {
            case PROJECT:
                List<QueryProjectVo> projectDtoList =
                        idList.stream().map(
                                x -> projectService.queryProjectById(x, requestMetaInfo)
                        ).collect(Collectors.toList());
                return (List<T>) projectDtoList;
            case POST:
                List<QueryPostVo> postDtoList =
                        idList.stream().map(
                                x -> postService.queryPostById(x, requestMetaInfo)
                        ).collect(Collectors.toList());
                return (List<T>) postDtoList;
            case COMMENT:
                List<QueryCommentVo> commentDtoList = idList.stream().map(
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

    @Override
    public List<UserVo> queryFollowerList(String username) {
        User targetUser = userRepository.internalQueryUserByUsername(username);

        List<Long> userIdList = followRepository.queryFollowerIdList(targetUser.getUserId());
        return userIdList.stream().map(x -> {
                    UserDto userDto = internalQueryUserByUserId(x);
                    return new UserVo()
                            .setUsername(userDto.getUsername())
                            .setDescription(userDto.getDescription())
                            .setProfileImage(userDto.getProfileImage())
                            .setViews(userDto.getViews())
                            .setFollowers(userDto.getFollowers())
                            .setFollowings(userDto.getFollowings());
                }
        ).toList();
    }

    @Override
    public List<UserVo> queryFollowingList(String username) {
        User targetUser = userRepository.internalQueryUserByUsername(username);

        List<Long> userIdList = followRepository.queryFollowingIdList(targetUser.getUserId());
        return userIdList.stream().map(x -> {
                    UserDto userDto = internalQueryUserByUserId(x);
                    return new UserVo()
                            .setUsername(userDto.getUsername())
                            .setDescription(userDto.getDescription())
                            .setProfileImage(userDto.getProfileImage())
                            .setViews(userDto.getViews())
                            .setFollowers(userDto.getFollowers())
                            .setFollowings(userDto.getFollowings());
                }
        ).toList();
    }

    @Override
    public List<UserVo> queryPendingList(String username) {
        User targetUser = userRepository.internalQueryUserByUsername(username);

        List<Long> userIdList = followRepository.queryPendingIdList(targetUser.getUserId());
        return userIdList.stream().map(x -> {
                    UserDto userDto = internalQueryUserByUserId(x);
                    return new UserVo()
                            .setUsername(userDto.getUsername())
                            .setDescription(userDto.getDescription())
                            .setProfileImage(userDto.getProfileImage())
                            .setViews(userDto.getViews())
                            .setFollowers(userDto.getFollowers())
                            .setFollowings(userDto.getFollowings());
                }
        ).toList();
    }
}
