package com.miemiemie.starter.core.exception;

import com.miemiemie.starter.core.enums.ResultStatus;
import lombok.Getter;

import java.io.Serial;

/**
 * 业务异常
 *
 * @author 杨顺翔
 * @since 2022/07/31
 */
@Getter
public class BizException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    protected int errorCode;

    protected String errorMsg;

    public BizException() {
        super();
    }

    public BizException(ResultStatus resultStatus) {
        super(resultStatus.getCode().toString());
        this.errorCode = resultStatus.getCode();
        this.errorMsg = resultStatus.getMessage();
    }

    public BizException(ResultStatus errorInfoInterface, Throwable cause) {
        super(errorInfoInterface.getCode().toString(), cause);
        this.errorCode = errorInfoInterface.getCode();
        this.errorMsg = errorInfoInterface.getMessage();
    }

    public BizException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public BizException(int errorCode, String errorMsg) {
        super(String.valueOf(errorCode));
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BizException(int errorCode, String errorMsg, Throwable cause) {
        super(String.valueOf(errorCode), cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
