package com.connect.common.enums;

public enum UserRole {
    ADMIN(0),
    ESSENTIAL(1),
    PLUS(2),
    PREMIUM(3);

    final int code;

    UserRole(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static String getRole(int code) {
        for (UserRole role : values()) {
            if (role.getCode() == code) {
                return role.name();
            }
        }
        return null;
    }
}
