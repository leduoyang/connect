package com.connect.web.controller.root;

import com.connect.api.common.APIResponse;
import com.connect.api.root.IRootApi;
import com.connect.api.user.dto.UserDto;
import com.connect.common.enums.UserRole;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.user.IUserService;
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
    public APIResponse queryTestToken(@RequestHeader String isRoot, @Validated String mockId) {
        if (isRoot == null || !Boolean.parseBoolean(isRoot)) {
            throw new ConnectDataException(
                    ConnectErrorCode.UNAUTHORIZED_EXCEPTION
            );
        }

        String role = UserRole.ADMIN.toString();
        String userId = UserRole.ROOT.toString();
        if (mockId != null) {
            UserDto mockUser = userService.internalQueryUserByUserId(mockId);
            if (mockUser == null) {
                throw new ConnectDataException(
                        ConnectErrorCode.UNAUTHORIZED_EXCEPTION,
                        "mockId not found"
                );
            }
            userId = mockUser.getUserId();
            role = UserRole.getRole(mockUser.getRole());
        }
        String token = jwtTokenUtil.generateToken(userId, role);

        return APIResponse.getOKJsonResult(token);
    }
}

