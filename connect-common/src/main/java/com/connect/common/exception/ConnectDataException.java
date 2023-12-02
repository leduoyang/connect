package com.connect.common.exception;

public class ConnectDataException extends RuntimeException {
    private ConnectErrorCode code;
    private String errorCode;
    private String errorMsg;

    public ConnectDataException(ConnectErrorCode code) {
        super(code.getCode() + ":" + code.getMsg());
        this.code = code;
        this.errorCode = code.getCode();
        this.errorMsg = code.getMsg();
    }

    public ConnectDataException(ConnectErrorCode code, String msg) {
        super(code.getCode() + ":" + code.getMsg() + ":" + msg);
        this.code = code;
        this.errorCode = code.getCode();
        this.errorMsg = code.getMsg() + ":" + msg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public ConnectErrorCode getCode() {
        return code;
    }
}
