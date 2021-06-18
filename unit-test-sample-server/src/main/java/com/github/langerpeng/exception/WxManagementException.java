package com.github.langerpeng.exception;

import com.github.langerpeng.constans.WxManagementExceptionType;
import lombok.Getter;
import lombok.ToString;

/**
 * @author langer_peng
 */
@ToString
public class WxManagementException extends RuntimeException {

    @Getter
    private final int code;

    public WxManagementException(int code) {
        this.code = code;
    }

    public WxManagementException(WxManagementExceptionType type) {
        super(type.getMsg());
        this.code = type.getCode();
    }

    public WxManagementException(WxManagementExceptionType type, String value) {
        super(type.getMsg() + value);
        this.code = type.getCode();
    }

    public WxManagementException(WxManagementExceptionType type, Throwable cause) {
        super(type.getMsg(), cause);
        this.code = type.getCode();
    }

    public WxManagementException(int code, String message) {
        super(message);
        this.code = code;
    }

    public WxManagementException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public WxManagementException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
