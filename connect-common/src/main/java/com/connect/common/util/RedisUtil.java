package com.connect.common.util;

import com.connect.common.enums.RedisPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    private static final String KEY_PREFIX = "signup:verification:";  // Set your desired prefix here

    @Autowired
    private RedisTemplate redisTemplate;

    public void setValueWithExpiration(RedisPrefix prefix, String key, Object value, long expirationTime, TimeUnit timeUnit) {
        key = prefix.getPrefix() + key;
        redisTemplate.opsForValue().set(key, value, expirationTime, timeUnit);
    }

    public Object getValue(RedisPrefix prefix, String key) {
        key = prefix.getPrefix() + key;
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteKey(RedisPrefix prefix, String key) {
        key = prefix.getPrefix() + key;
        redisTemplate.delete(key);
    }
}


