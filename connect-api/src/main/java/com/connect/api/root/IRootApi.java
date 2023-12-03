package com.connect.api.root;

import com.connect.api.root.request.RootLoginRequest;
import com.connect.api.common.APIResponse;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/root")
public interface IRootApi {
    @PostMapping("/test/token")
    APIResponse queryTestToken(@RequestHeader(required = false) String isRoot);
}