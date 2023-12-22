package com.connect.api.star;

import com.connect.api.common.APIResponse;
import com.connect.api.star.request.QueryStarRequest;
import com.connect.api.star.request.StarRequest;
import com.connect.api.star.request.UnStarRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RequestMapping(value = "/api/connect/v1")
public interface IStarApi {
    @PostMapping(value = "/star")
    APIResponse<Void> star(
            @Validated @RequestBody StarRequest request
    );

    @DeleteMapping(value = "/star")
    APIResponse<Void> removeStar(
            @Validated @RequestBody UnStarRequest request
    );

    @GetMapping(value = "/starExisting")
    APIResponse<Map<String, Boolean>> starExisting(
            @Validated QueryStarRequest request
    );
}
