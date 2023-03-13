package com.miemiemie.starter.data.protection;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yangshunxiang
 * @since 2023/3/12
 */
@ConfigurationProperties(prefix = DataProtectionProperties.PROPERTIES_PREFIX)
@Data
public class DataProtectionProperties {

    public static final String PROPERTIES_PREFIX = "miemiemie.data.protection";

    public static final String PROPERTIES_PREFIX_ENABLE = "miemiemie.data.protection.enable";

    /**
     * 启用数据保护
     */
    private boolean enable = true;

    private Strategy strategy = new Strategy();

    @Data
    public static class Strategy {

        private Md5 md5 = new Md5();

    }

    @Data
    public static class Md5 {

        private String salt;

    }

}
