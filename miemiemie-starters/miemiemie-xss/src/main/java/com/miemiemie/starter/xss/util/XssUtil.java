package com.miemiemie.starter.xss.util;

import com.miemiemie.starter.core.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author yangshunxiang
 * @since 2023/4/2
 */
@Slf4j
public class XssUtil {

    public static String cleanXss(String value, Policy policy) {
        if (Objects.isNull(value) || Objects.isNull(policy)) {
            return null;
        }
        if (!StringUtils.hasText(value)) {
            return value;
        }
        AntiSamy antiSamy = new AntiSamy(policy);
        try {
            return antiSamy.scan(value).getCleanHTML();
        } catch (ScanException | PolicyException e) {
            log.error("XSS防护异常", e);
            throw new BizException("XSS防护异常");
        }
    }
}
