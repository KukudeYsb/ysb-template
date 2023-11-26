package com.commen;

import com.constants.ResultCode;
import lombok.EqualsAndHashCode;

/**
 * 自定义业务异常类
 *
 * @author Joy Yang
 */
@EqualsAndHashCode(callSuper = true)
public class SystemException extends RuntimeException{

    private ResultCode resultCode;
    private Integer code;
    private String msg;

    public SystemException() {
        super();
    }

    public SystemException(ResultCode resultCode) {
        super("{code:" + resultCode.getCode() + ",Msg:" + resultCode.getMessage() + "}");
        this.resultCode = resultCode;
        this.code = resultCode.getCode();
        this.msg = resultCode.getMessage();
    }

    public SystemException(Integer code, String msg) {
        super("{code:" + code + ",Msg:" + msg + "}");
        this.code = code;
        this.msg = msg;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public SystemException(Integer code, String msg, Object... args) {
        super("{code:" + code + ",Msg:" + String.format(msg, args) + "}");
        this.code = code;
        this.msg = String.format(msg, args);
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}