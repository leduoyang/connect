package com.connect.common.enums;

public enum CommentStatus {
    PUBLIC(0),
    SEMI(1),
    PRIVATE(2),
    DELETED(3);

    final int code;

    CommentStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
