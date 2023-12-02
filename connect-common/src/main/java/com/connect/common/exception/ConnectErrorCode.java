package com.connect.common.exception;

import lombok.Getter;

@Getter
public enum ConnectErrorCode {
    BLANK("000000", "Default error code"),
    INTERNAL_SERVER_ERROR("100001", "Internal server error"),
    ILLEGAL_REQUESTER_ERROR("100002", "Illegal Requester"),
    PARAM_EXCEPTION("100003", "Parameters error"),
    UNAUTHORIZED_EXCEPTION("100004", "Not Authorized for this action."),

    /**
     * User
     */
    USER_CREATE_EXCEPTION("300001", "Create user failed"),
    USER_UPDATE_EXCEPTION("300002", "Update user failed"),
    USER_DELETE_EXCEPTION("300003", "Delete user failed"),
    USER_NOT_EXISTED_EXCEPTION("300004", "User not existed."),
    USER_EXISTED_EXCEPTION("300005", "User has existed."),

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
    POST_NOT_EXISTED_EXCEPTION("300004", "Post not existed.");

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
