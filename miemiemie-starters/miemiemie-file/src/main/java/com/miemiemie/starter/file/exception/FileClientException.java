package com.miemiemie.starter.file.exception;

/**
 * 文件客户端操作异常
 *
 * @author yangshunxiang
 * @since 2023/2/23
 */
public class FileClientException extends RuntimeException {

    public FileClientException() {
    }

    public FileClientException(String message) {
        super(message);
    }

    public FileClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileClientException(Throwable cause) {
        super(cause);
    }

    public FileClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
