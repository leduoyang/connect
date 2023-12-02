package com.connect.common.enums;

public enum RedisPrefix {
    USER_SIGNUP_EMAIL("connect:user:signup:email:");

    final String prefix;

    RedisPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
