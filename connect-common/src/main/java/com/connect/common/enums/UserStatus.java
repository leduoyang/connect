package com.connect.common.enums;

public enum UserStatus {
    DELETED(0),
    PUBLIC(1),
    PRIVATE(2),
    TESTED(3);

    final int code;

    UserStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
