package com.connect.common.enums;

public enum SocialLinkMap {
    LINKEDIN("linkedin"),
    GITHUB("github"),
    INSTAGRAM("instagram"),
    PERSONAL_WEBSITE("personal_website");

    final String key;

    SocialLinkMap(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
