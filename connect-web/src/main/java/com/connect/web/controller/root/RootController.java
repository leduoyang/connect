package com.connect.web.controller.root;

import com.connect.api.root.IRootApi;
import com.connect.api.root.request.RootLoginRequest;
import com.connect.api.common.APIResponse;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.user.IUserService;
import com.connect.web.util.JwtTokenUtil;
import com.connect.web.common.AppContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@ConditionalOnProperty(name = "root.controller.enabled", havingValue = "true")
@Profile({"dev"})
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
    public APIResponse testLogin(@RequestBody RootLoginRequest request) {
        String token = null;
        if (userService.authenticateRootUser(request)) {
            token = jwtTokenUtil.generateToken(request.getUserId(), "");
        }
        return APIResponse.getOKJsonResult(token);
    }
}

