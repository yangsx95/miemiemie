package com.miemiemie.core.exception;

import com.miemiemie.core.enums.ResultStatus;
import lombok.Data;

/**
 * @author yangshunxiang
 * @since 2023/1/13
 */
@Data
public class ExceptionInfo implements ResultStatus {

    private Integer code;

    private String message;

}
