package com.connect.common.util;

import com.connect.common.enums.RedisPrefix;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    private static final String KEY_PREFIX = "signup:verification:";  // Set your desired prefix here

    @Autowired
    private RedisTemplate redisTemplate;

    public void setValueWithExpiration(RedisPrefix prefix, String key, Object value, long expirationTime, TimeUnit timeUnit) {
        try {
            key = prefix.getPrefix() + key;
            redisTemplate.opsForValue().set(key, value, expirationTime, timeUnit);
        } catch (RedisSystemException e) {
            throw new ConnectDataException(
                    ConnectErrorCode.INTERNAL_SERVER_ERROR,
                    "Set value from Redis failed. Error message: " + e
            );
        }
    }

    public Object getValue(RedisPrefix prefix, String key) {
        try {
            key = prefix.getPrefix() + key;
            return redisTemplate.opsForValue().get(key);
        } catch (RedisSystemException e) {
            throw new ConnectDataException(
                    ConnectErrorCode.INTERNAL_SERVER_ERROR,
                    "Get value from Redis failed. Error message: " + e
            );
        }
    }

    public void deleteKey(RedisPrefix prefix, String key) {
        try {
            key = prefix.getPrefix() + key;
            redisTemplate.delete(key);
        } catch (RedisSystemException e) {
            throw new ConnectDataException(
                    ConnectErrorCode.INTERNAL_SERVER_ERROR,
                    "Delete key from Redis failed. Error message: " + e
            );
        }
    }
}


