package com.connect.web.controller.root;

import com.connect.api.common.APIResponse;
import com.connect.api.root.IRootApi;
import com.connect.common.enums.UserRole;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.user.IUserService;
import com.connect.data.dto.UserDto;
import com.connect.web.util.JwtTokenUtil;
import com.connect.web.common.AppContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RootController implements IRootApi {
    @Autowired
    private AppContext appConfig;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final IUserService userService;

    public RootController(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public APIResponse<String> queryTestToken(@RequestHeader String isRoot, @Validated String mockUsername) {
        if (isRoot == null || !Boolean.parseBoolean(isRoot)) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION
            );
        }

        UserDto mockUser;
        String role;
        if (mockUsername == null) {
            mockUser = userService.internalQueryUserByUsername(UserRole.ROOT.toString());
            role = UserRole.getRole(mockUser.getRole());
        } else {
            mockUser = userService.internalQueryUserByUsername(mockUsername);
            if (mockUser == null) {
                throw new ConnectDataException(
                        ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                        "mockUser not found"
                );
            }
            role = UserRole.getRole(mockUser.getRole());
        }
        String token = jwtTokenUtil.generateToken(mockUser.getUserId(), role);

        return APIResponse.getOKJsonResult(token);
    }
}

