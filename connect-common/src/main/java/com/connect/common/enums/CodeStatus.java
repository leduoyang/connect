package com.connect.common.enums;

public enum CodeStatus {
    DELETED(0),
    PENDING(1),
    COMPLETED(2),
    EXPIRED(3);

    final int code;

    CodeStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static String getStatus(int code) {
        for (CodeStatus status : values()) {
            if (status.getCode() == code) {
                return status.name();
            }
        }
        return null;
    }
}
