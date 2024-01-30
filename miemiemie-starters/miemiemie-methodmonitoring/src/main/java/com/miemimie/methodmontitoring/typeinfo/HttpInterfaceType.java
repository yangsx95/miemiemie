package com.miemimie.methodmontitoring.typeinfo;

import com.miemimie.methodmontitoring.Type;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yangshunxiang
 * @since 2024/1/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HttpInterfaceType extends Type {

    /**
     * 接口url
     */
    private String url;

    /**
     * 接口请求方法
     */
    private String method;

    /**
     * http 状态码
     */
    private int httpStatus;

    /**
     * 业务类型
     */
    private String businessType;
}
