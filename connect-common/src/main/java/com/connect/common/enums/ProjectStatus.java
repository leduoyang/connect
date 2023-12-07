package com.connect.common.enums;

public enum ProjectStatus {
    PUBLIC(0),
    SEMI(1),
    PRIVATE(2),
    DELETED(3);

    final int code;

    ProjectStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
