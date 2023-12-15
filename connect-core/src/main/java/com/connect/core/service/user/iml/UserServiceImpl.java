package com.connect.core.service.user.iml;

import com.connect.api.comment.dto.QueryCommentDto;
import com.connect.api.post.dto.QueryPostDto;
import com.connect.api.project.dto.QueryProjectDto;
import com.connect.api.root.request.RootLoginRequest;
import com.connect.api.user.dto.UserDto;
import com.connect.api.user.request.*;
import com.connect.common.enums.StarTargetType;
import com.connect.common.enums.UserStatus;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.common.util.ImageUploadUtil;
import com.connect.core.service.comment.ICommentService;
import com.connect.core.service.post.IPostService;
import com.connect.core.service.project.IProjectService;
import com.connect.core.service.user.IUserService;
import com.connect.data.entity.Profile;
import com.connect.data.entity.User;
import com.connect.data.param.QueryUserParam;
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

    private IProjectService projectService;

    private IPostService postService;

    private ICommentService commentService;

    public UserServiceImpl(
            IUserRepository userRepository,
            IStarRepository starRepository,
            IProjectService projectService,
            IPostService postService,
            ICommentService commentService
    ) {
        this.userRepository = userRepository;
        this.starRepository = starRepository;
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
    public void editUser(String userId, EditUserRequest request) {
        User user = new User()
                .setUserId(request.getUserId())
                .setPassword(request.getPassword())
                .setDescription(request.getDescription())
                .setEmail(request.getEmail())
                .setPhone(request.getPhone())
                .setProfileImage(request.getProfileImage());

        if (request.getStatus() != null) {
            if (request.getStatus() < 0 || request.getStatus() > 3) {
                throw new ConnectDataException(
                        ConnectErrorCode.PARAM_EXCEPTION,
                        "Invalid payload (status should be between 0 and 3)"
                );
            }
            user.setStatus(request.getStatus());
        }

        userRepository.editUser(userId, user);
    }

    @Override
    public void editUserProfile(String userId, EditProfileRequest request) {
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

        userRepository.editUserProfile(userId, profile);
    }

    @Override
    public void editProfileImage(String userId, MultipartFile image) {
        User user = userRepository.queryUserByUserId(userId);
        String profileImage =
                imageUploadUtil.profileImage(
                        user.getId() + "." + imageUploadUtil.getExtension(image),
                        image
                );

        EditProfileRequest editProfileRequest = new EditProfileRequest()
                .setProfileImage(profileImage);
        editUserProfile(userId, editProfileRequest);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteUser(userId);
    }

    @Override
    public UserDto queryUserByUserId(String userId) {
        User user = userRepository.queryUserByUserId(userId);
        userRepository.incrementViews(
                user.getUserId(),
                user.getVersion()
        );

        UserDto userDto = new UserDto()
                .setUserId(user.getUserId())
                .setStatus(user.getStatus())
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
    public List<UserDto> queryUser(QueryUserRequest request) {
        QueryUserParam param = new QueryUserParam().setKeyword(request.getKeyword());
        List<User> userList = userRepository.queryUser(param);

        return userList
                .stream()
                .map(x -> new UserDto()
                        .setUserId(x.getUserId())
                        .setStatus(x.getStatus())
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
    public <T> List<T> queryUserStarList(String userId, StarTargetType targetType, Class<T> returnClass) {
        List<Integer> idList = starRepository.queryTargetIdList(targetType.getCode(), userId);
        switch (Objects.requireNonNull(targetType)) {
            case PROJECT:
                List<QueryProjectDto> projectDtoList =
                        idList.stream().map(
                                x -> projectService.queryProjectById(x)
                        ).collect(Collectors.toList());
                return (List<T>) projectDtoList;
            case POST:
                List<QueryPostDto> postDtoList =
                        idList.stream().map(
                                x -> postService.queryPostById(x)
                        ).collect(Collectors.toList());
                return (List<T>) postDtoList;
            case COMMENT:
                List<QueryCommentDto> commentDtoList = idList.stream().map(
                        x -> commentService.queryCommentById(x)
                ).collect(Collectors.toList());
                return (List<T>) commentDtoList;
            default:
                throw new ConnectDataException(
                        ConnectErrorCode.INTERNAL_SERVER_ERROR,
                        "Invalid payload updating like counts for target entity"
                );
        }
    }
}
