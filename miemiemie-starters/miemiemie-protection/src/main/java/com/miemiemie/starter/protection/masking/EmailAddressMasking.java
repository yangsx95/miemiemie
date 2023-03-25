package com.miemiemie.starter.protection.masking;

/**
 * 邮箱地址脱敏
 *
 * @author yangshunxiang
 * @since 2023/3/25
 */
public class EmailAddressMasking extends AbstractStringMasking {

    @Override
    protected String doMask(String email) {
        int index = email.indexOf("@");
        if (index <= 0) {
            return email;
        }
        String prefix = email.substring(0, index);
        String suffix = email.substring(index);
        if (prefix.length() <= 3) {
            return prefix.replaceAll("\\w", "*") + suffix;
        }
        return prefix.substring(0, 3).replaceAll("\\w", "*") + prefix.substring(3) + suffix;
    }
}
