package com.connect.common.enums;

public enum FollowStatus {
    UNFOLLOW(0),
    PENDING(1),
    APPROVED(2),
    REJECTED(3);

    final int code;

    FollowStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static String getStatus(int code) {
        for (FollowStatus status : values()) {
            if (status.getCode() == code) {
                return status.name();
            }
        }
        return null;
    }
}
