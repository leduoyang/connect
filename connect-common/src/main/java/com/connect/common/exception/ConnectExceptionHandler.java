package com.connect.common.exception;

import com.connect.api.common.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.UnexpectedTypeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import java.net.BindException;

@Slf4j
@ControllerAdvice
public class ConnectExceptionHandler {
    @ExceptionHandler(value = ConnectDataException.class)
    @ResponseBody
    public Object exception(ConnectDataException exception, HandlerMethod handler, HttpServletRequest request, HttpServletResponse response) {
        log.warn("ConnectDataException {}", exception);
        return this.buildErrorResult(exception);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    @ResponseBody
    public Object validationExceptionHandler(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        log.error("MethodArgumentNotValidException {}", exception);
        return this.buildErrorResult(exception);
    }

    @ExceptionHandler(value = Throwable.class)
    @ResponseBody
    public Object exception(HttpServletResponse response, Throwable exception, HttpServletRequest request) {
        log.error("OtherException {}", exception);
        return this.buildErrorResult(exception);
    }

    private Object buildErrorResult(Throwable error) {
        APIResponse.Type type;
        String code;
        String msg;

        if (error instanceof IllegalArgumentException) {
            type = APIResponse.Type.INVALID;
            IllegalArgumentException ex = (IllegalArgumentException) error;
            code = ConnectErrorCode.PARAM_EXCEPTION.getCode();
            msg = ex.getMessage();
        } else if (error instanceof ConnectDataException) {
            type = APIResponse.Type.GENERAL;
            ConnectDataException ex = (ConnectDataException) error;
            code = ex.getErrorCode();
            msg = ex.getErrorMsg();
        } else if (error instanceof MethodArgumentNotValidException) {
            type = APIResponse.Type.INVALID;
            BindingResult bindResult = ((MethodArgumentNotValidException) error).getBindingResult();
            code = ConnectErrorCode.PARAM_EXCEPTION.getCode();
            msg = bindResult.getAllErrors().get(0).getDefaultMessage();
        } else if (error instanceof UnexpectedTypeException) {
            type = APIResponse.Type.INVALID;
            UnexpectedTypeException ex = (UnexpectedTypeException) error;
            code = ConnectErrorCode.PARAM_EXCEPTION.getCode();
            msg = ex.getMessage();
        }else {
            type = APIResponse.Type.SYS;
            code = ConnectErrorCode.INTERNAL_SERVER_ERROR.getCode();
            msg = ConnectErrorCode.INTERNAL_SERVER_ERROR.getMsg();
        }

        return APIResponse.getErrorJsonResult(type.getCode(), code, msg);
    }

}
