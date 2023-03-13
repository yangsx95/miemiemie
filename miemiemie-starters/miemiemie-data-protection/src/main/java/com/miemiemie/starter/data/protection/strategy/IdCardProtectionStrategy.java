package com.miemiemie.starter.data.protection.strategy;

import com.miemiemie.starter.data.protection.ProtectionStrategy;

/**
 * 身份证号码脱敏策略
 *
 * @author yangshunxiang
 * @since 2023/3/12
 */
public class IdCardProtectionStrategy implements ProtectionStrategy {

    @Override
    public Object protect(Object data) {
        if (data == null) {
            return null;
        }
        if (!(data instanceof String)) {
            return data;
        }
        return maskIdCard((String) data);
    }

    public static String maskIdCard(String idCard) {
        if (idCard == null) {
            return null;
        }
        // 身份证号码正则表达式，15位和18位
        String regex = "(?<=\\w{3})\\w(?=\\w{4})";
        String replacement = "*";
        // 对于18位身份证号码，中间四位用*替换
        if (idCard.length() == 18) {
            regex = "(?<=\\w{6})\\w(?=\\w{4})";
        }
        return idCard.replaceAll(regex, replacement);
    }

}
