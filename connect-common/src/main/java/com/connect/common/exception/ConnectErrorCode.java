package com.connect.common.exception;

import lombok.Getter;

@Getter
public enum ConnectErrorCode {
    BLANK("000000", "Default error code"),
    INTERNAL_SERVER_ERROR("100001", "Internal server error"),
    ILLEGAL_REQUESTER_ERROR("100002", "Not permitted for this request"),
    PARAM_EXCEPTION("100003", "Parameters error"),
    UNAUTHORIZED_EXCEPTION("100004", "Not authorized for this action."),
    OPTIMISTIC_LOCK_CONFLICT_EXCEPTION("100005", "Optimistic lock conflict arises."),

    /**
     * User
     */
    USER_CREATE_EXCEPTION("300001", "Create user failed"),
    USER_EDIT_EXCEPTION("300002", "Update user failed"),
    USER_DELETE_EXCEPTION("300003", "Delete user failed"),
    USER_NOT_EXISTED_EXCEPTION("300004", "User not existed."),
    USER_EXISTED_EXCEPTION("300005", "User has existed."),

    /**
     * Social Link
     */
    SOCIAL_LINK_CREATE_EXCEPTION("300001", "Create social link failed"),
    SOCIAL_LINK_UPDATE_EXCEPTION("300002", "Update social link failed"),
    SOCIAL_LINK_DELETE_EXCEPTION("300003", "Delete social link failed"),
    SOCIAL_LINK_NOT_EXISTED_EXCEPTION("300004", "Social link not existed."),
    SOCIAL_LINK_EXISTED_EXCEPTION("300005", "Social link has existed."),

    /**
     * Experience
     */
    EXPERIENCE_CREATE_EXCEPTION("300001", "Create experience failed"),
    EXPERIENCE_UPDATE_EXCEPTION("300002", "Update experience failed"),
    EXPERIENCE_DELETE_EXCEPTION("300003", "Delete experience failed"),
    EXPERIENCE_NOT_EXISTED_EXCEPTION("300004", "Experience not existed."),
    EXPERIENCE_EXISTED_EXCEPTION("300005", "Experience has existed."),

    /**
     * User Verification
     */
    USER_VERIFICATION_CREATE_EXCEPTION("300001", "Create user verification failed"),
    USER_VERIFICATION_UPDATE_EXCEPTION("300002", "Update user verification failed"),
    USER_VERIFICATION_DELETE_EXCEPTION("300003", "Delete user verification failed"),
    USER_VERIFICATION_NOT_EXISTED_EXCEPTION("300004", "User verification not existed."),
    USER_VERIFICATION_EXISTED_EXCEPTION("300005", "User verification has existed."),

    /**
     * Comment
     */
    COMMENT_CREATE_EXCEPTION("300001", "Create comment failed"),
    COMMENT_UPDATE_EXCEPTION("300002", "Update comment failed"),
    COMMENT_DELETE_EXCEPTION("300003", "Delete comment failed"),
    COMMENT_NOT_EXISTED_EXCEPTION("300004", "Comment not existed."),

    /**
     * Project
     */
    PROJECT_CREATE_EXCEPTION("300001", "Create project failed"),
    PROJECT_UPDATE_EXCEPTION("300002", "Update project failed"),
    PROJECT_DELETE_EXCEPTION("300003", "Delete project failed"),
    PROJECT_NOT_EXISTED_EXCEPTION("300004", "Project not existed."),

    /**
     * Post
     */
    POST_CREATE_EXCEPTION("300001", "Create post failed"),
    POST_UPDATE_EXCEPTION("300002", "Update post failed"),
    POST_DELETE_EXCEPTION("300003", "Delete post failed"),
    POST_NOT_EXISTED_EXCEPTION("300004", "Post not existed."),

    /**
     * Follow
     */
    FOLLOW_CREATE_EXCEPTION("300001", "Create follow failed"),
    FOLLOW_UPDATE_EXCEPTION("300002", "Update follow failed"),
    FOLLOW_DELETE_EXCEPTION("300003", "Delete follow failed"),
    FOLLOW_NOT_EXISTED_EXCEPTION("300004", "Follow not existed."),
    FOLLOW_EXISTED_EXCEPTION("300005", "Follow already existed."),

    /**
     * Star
     */
    STAR_CREATE_EXCEPTION("300001", "Create star failed"),
    STAR_UPDATE_EXCEPTION("300002", "Update star failed"),
    STAR_DELETE_EXCEPTION("300003", "Delete star failed"),
    STAR_NOT_EXISTED_EXCEPTION("300004", "Star not existed."),
    STAR_EXISTED_EXCEPTION("300005", "Star already existed.");

    final String code;
    final String msg;

    ConnectErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
