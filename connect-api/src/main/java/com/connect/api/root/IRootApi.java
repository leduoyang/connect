package com.connect.api.root;

import com.connect.api.root.request.RootLoginRequest;
import com.connect.api.common.APIResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/api/root")
public interface IRootApi {
    @PostMapping("/test/login")
    APIResponse testLogin(@RequestBody RootLoginRequest request);
}