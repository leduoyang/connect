package com.connect.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisUtil {
    private static final String REDIS_KEY_PREFIX = "jwtToken:";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void storeToken(String userId, String token, long expirationTimeInMinutes) {
        String key = REDIS_KEY_PREFIX + userId;
        redisTemplate.opsForValue().set(key, token, expirationTimeInMinutes, TimeUnit.MINUTES);
    }

    public String retrieveToken(String userId) {
        String key = REDIS_KEY_PREFIX + userId;
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteToken(String userId) {
        String key = REDIS_KEY_PREFIX + userId;
        redisTemplate.delete(key);
    }
}

