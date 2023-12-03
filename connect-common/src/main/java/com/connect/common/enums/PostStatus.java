package com.connect.common.enums;

public enum PostStatus {
    DELETED(0),
    PUBLIC(1),
    PRIVATE(2),
    TESTED(3);

    final int code;

    PostStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
