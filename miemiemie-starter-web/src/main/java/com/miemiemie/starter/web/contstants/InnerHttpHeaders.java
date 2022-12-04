package com.miemiemie.starter.web.contstants;

import com.miemiemie.core.enums.CommonEnum;
import com.miemiemie.core.enums.NameCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.http.HttpHeaders;

import java.util.Objects;
import java.util.Optional;

/**
 * 内部请求头对象
 *
 * @author 杨顺翔
 * @since 2022/07/31
 */
@Data
@NoArgsConstructor
@FieldNameConstants
public class InnerHttpHeaders {

    /**
     * 当前请求的来源
     */
    private String _MMM_SERVICE_TYPE;

    public ServiceTypeEnum getServiceTypeEnum() {
        return CommonEnum.getEnum(this._MMM_SERVICE_TYPE, ServiceTypeEnum.class);
    }

    public static InnerHttpHeaders build(HttpHeaders httpHeaders) {
        InnerHttpHeaders innerHttpHeaders = new InnerHttpHeaders();
        if (Objects.isNull(httpHeaders) || httpHeaders.isEmpty()) {
            return innerHttpHeaders;
        }

        innerHttpHeaders._MMM_SERVICE_TYPE = Optional.ofNullable(httpHeaders.get(Fields._MMM_SERVICE_TYPE)).map(e -> e.get(0)).orElse(null);
        return innerHttpHeaders;
    }

    @Getter
    @AllArgsConstructor
    public enum ServiceTypeEnum implements NameCodeEnum {
        SERVICE("内部服务"),
        WEB("web发送"),
        ;

        private final String message;
    }


}
