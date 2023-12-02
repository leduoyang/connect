package com.connect.core.service.user.iml;

import com.connect.api.root.request.RootLoginRequest;
import com.connect.api.user.dto.UserDto;
import com.connect.api.user.request.CreateUserRequest;
import com.connect.api.user.request.QueryUserRequest;
import com.connect.api.user.request.UpdateUserRequest;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.user.IUserService;
import com.connect.data.entity.User;
import com.connect.data.param.QueryUserParam;
import com.connect.data.repository.IUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserServiceImpl implements IUserService {
    private IUserRepository userRepository;

    public UserServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
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
    public UserDto queryUserByUserId(String userId) {
        User user = userRepository.queryUserByUserId(userId);

        UserDto userDto = new UserDto()
                .setUserId(user.getUserId())
                .setStatus(user.getStatus())
                .setDescription(user.getDescription())
                .setProfileImage(user.getProfileImage())
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
                        .setDbModifyTime(x.getDbModifyTime())
                )
                .collect(Collectors.toList());
    }

    @Override
    public void createUser(CreateUserRequest request) {
        User user = new User()
                .setUserId(request.getUserId())
                .setPassword(request.getPassword())
                .setStatus(request.getStatus())
                .setRole(request.getRole())
                .setDescription(request.getDescription())
                .setEmail(request.getEmail())
                .setPhone(request.getPhone());

        userRepository.createUser(user);
    }

    @Override
    public void updateUser(String userId, UpdateUserRequest request) {
        User user = new User()
                .setUserId(userId)
                .setPassword(request.getPassword())
                .setStatus(request.getStatus())
                .setRole(request.getRole())
                .setDescription(request.getDescription())
                .setEmail(request.getEmail())
                .setPhone(request.getPhone())
                .setProfileImage(request.getProfileImage());

        userRepository.updateUser(user);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteUser(userId);
    }
}
