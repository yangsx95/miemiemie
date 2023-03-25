package com.miemiemie.starter.protection.masking;

/**
 * ip地址脱敏
 *
 * @author yangshunxiang
 * @since 2023/3/25
 */
public class IpAddressMasking extends AbstractStringMasking {

    @Override
    protected String doMask(String ipAddress) {
        return ipAddress.replaceAll("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.)(\\d{1,3})", "$1*");
    }
}
