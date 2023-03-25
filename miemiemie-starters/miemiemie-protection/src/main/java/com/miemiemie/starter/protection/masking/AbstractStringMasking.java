package com.miemiemie.starter.protection.masking;

import org.springframework.util.StringUtils;

/**
 * @author yangshunxiang
 * @since 2023/3/25
 */
public abstract class AbstractStringMasking implements DataMasking<String, String> {

    @Override
    public String mask(String data) {
        if (!StringUtils.hasText(data)) {
            return data;
        }
        return doMask(data);
    }

    protected abstract String doMask(String data);
}
