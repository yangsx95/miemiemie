package com.miemiemie.starter.data.protection.strategy;

import com.miemiemie.starter.data.protection.ProtectionStrategy;
import org.springframework.util.StringUtils;

/**
 * 手机号码脱敏处理
 *
 * @author yangshunxiang
 * @since 2023/3/12
 */
public class MobileProtectionStrategy implements ProtectionStrategy {

    @Override
    public Object protect(Object object) {
        if (object == null) {
            return null;
        }
        if (!(object instanceof String)) {
            return object;
        }
        return desensitizePhoneNumber((String) object);
    }

    public static String desensitizePhoneNumber(String phoneNumber) {
        if (!StringUtils.hasText(phoneNumber)) {
            return phoneNumber;
        }
        if (phoneNumber.length() != 11) {
            return phoneNumber;
        }
        return phoneNumber.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

}
