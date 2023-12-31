package com.connect.api.root;

import com.connect.api.common.APIResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/root")
public interface IRootApi {
    @PostMapping("/test/token")
    APIResponse queryTestToken(@RequestHeader String isRoot, @Validated String mockId);
}