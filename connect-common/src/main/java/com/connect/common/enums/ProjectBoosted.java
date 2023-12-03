package com.connect.common.enums;

public enum ProjectBoosted {
    DEFAULT(0),
    BOOSTED(1);

    final int code;

    ProjectBoosted(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
