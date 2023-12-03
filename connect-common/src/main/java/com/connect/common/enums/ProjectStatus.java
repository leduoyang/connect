package com.connect.common.enums;

public enum ProjectStatus {
    DELETED(0),
    PUBLIC(1),
    PRIVATE(2),
    TESTED(3);

    final int code;

    ProjectStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
