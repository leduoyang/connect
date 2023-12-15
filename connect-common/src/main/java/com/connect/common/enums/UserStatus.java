package com.connect.common.enums;

public enum UserStatus {
    PUBLIC(0),
    SEMI(1),
    PRIVATE(2),
    DELETED(3);

    final int code;

    UserStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static UserStatus getStatus(int code) {
        for (UserStatus status : values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }
}
