package com.miemiemie.starter.protection.masking;

/**
 * 手机号码脱敏处理
 *
 * @author yangshunxiang
 * @since 2023/3/12
 */
public class MobileMasking extends AbstractStringMasking {

    @Override
    protected String doMask(String phoneNumber) {
        if (phoneNumber.length() != 11) {
            return phoneNumber;
        }
        return phoneNumber.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

}
