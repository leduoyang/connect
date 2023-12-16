package com.connect.web.common;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Accessors(chain = true)
@Data
@Component
public class AppContext {
    public static String adminToken;

    public static Map<String, userContext> user = new HashMap<>();

    @Data
    public static class userContext {
        String userId = "";

        boolean isRoot = false;
    }
}
