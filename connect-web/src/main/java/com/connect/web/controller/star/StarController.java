package com.connect.web.controller.star;

import com.connect.api.common.APIResponse;
import com.connect.api.star.IStarApi;
import com.connect.api.star.dto.StarDto;
import com.connect.api.star.dto.UnStarDto;
import com.connect.api.star.request.QueryStarRequest;
import com.connect.api.star.request.StarRequest;
import com.connect.api.star.request.UnStarRequest;
import com.connect.core.service.star.IStarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class StarController implements IStarApi {
    private final IStarService starService;

    public StarController(IStarService starService) {
        this.starService = starService;
    }

    @Override
    public APIResponse<Void> star(StarRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        StarDto starDto = new StarDto()
                .setUserId(authentication.getName())
                .setTargetId(request.getTargetId())
                .setTargetType(request.getTargetType());

        starService.star(starDto);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Void> unStar(UnStarRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UnStarDto unStarDto = new UnStarDto()
                .setUserId(authentication.getName())
                .setTargetId(request.getTargetId())
                .setTargetType(request.getTargetType());

        starService.unStar(unStarDto);
        return APIResponse.getOKJsonResult(null);
    }

    @Override
    public APIResponse<Map<String, Boolean>> starExisting(QueryStarRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean existed = starService.starExisting(
                authentication.getName(),
                request.getTargetId(),
                request.getTargetType(),
                true
        );

        Map<String, Boolean> result = new HashMap<>();
        result.put("existed", existed);
        return APIResponse.getOKJsonResult(result);
    }
}
