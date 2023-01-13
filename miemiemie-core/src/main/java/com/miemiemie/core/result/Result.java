package com.miemiemie.core.result;

import com.miemiemie.core.enums.ResultStatus;
import com.miemiemie.core.enums.ResultStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

/**
 * 通用响应对象
 *
 * @author 杨顺翔
 * @since 2022/07/23
 */
@Data
@Accessors(chain = true)
@FieldNameConstants
public class Result<T> {

    /**
     * 状态码
     */
    private int code;

    /**
     * 描述
     */
    private String message;

    /**
     * 数据对象
     */
    private T data;

    /**
     * 构建任意响应
     *
     * @param code    状态码
     * @param message 描述
     * @param data    数据对象
     * @param <T>     数据对象的类型
     * @return result对象
     */
    public static <T> Result<T> build(int code, String message, T data) {
        return new Result<T>().setCode(code).setMessage(message).setData(data);
    }

    /**
     * 构建一个没有data的响应
     *
     * @param code    状态码
     * @param message 描述
     * @return result对象
     */
    public static Result<Void> build(int code, String message) {
        return build(code, message, null);
    }

    /**
     * 从 ResultStatus 接口构造响应结果对象
     *
     * @param resultStatus 响应状态对象
     * @param data         数据对象
     * @param <T>          数据对象类型
     * @return 结果对象
     */
    public static <T> Result<T> build(ResultStatus resultStatus, T data) {
        return build(resultStatus.getCode(), resultStatus.getMessage(), data);
    }

    /**
     * 从 ResultStatus 接口构造没有data的响应结果对象
     *
     * @param resultStatus 响应状态对象
     * @return 结果对象
     */
    public static Result<Void> build(ResultStatus resultStatus) {
        return build(resultStatus, null);
    }

    /**
     * 构建通用的成功对象
     *
     * @param data 数据对象
     * @param <T>  数据对象类型
     * @return 结果对象
     * @see ResultStatusEnum
     */
    public static <T> Result<T> success(T data) {
        return build(ResultStatusEnum.SUCCESS, data);
    }

    /**
     * 构建通用的无返回数据的成功对象
     *
     * @return 结果对象
     */
    public static Result<Void> success() {
        return build(ResultStatusEnum.SUCCESS, null);
    }

    /**
     * 构建失败结果对象
     *
     * @return 失败结果对象
     */
    public static Result<Void> fail(ResultStatus resultStatus) {
        return build(resultStatus, null);
    }
}
