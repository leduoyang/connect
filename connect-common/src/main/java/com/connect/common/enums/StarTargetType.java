package com.connect.common.enums;

public enum StarTargetType {
    PROJECT(0),
    POST(1),
    COMMENT(2),
    USER(3);

    final int code;

    StarTargetType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static String getType(int code) {
        for (StarTargetType type : values()) {
            if (type.getCode() == code) {
                return type.name();
            }
        }
        return null;
    }
}
