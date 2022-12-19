package com.miemiemie.starter.xxljob;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * xxl-job 配置类
 *
 * @author yangshunxiang
 * @since 2022/12/20
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "xxl.job")
public class XxlJobConfig {


    private String accessToken;

    private AdminConfig admin = new AdminConfig();

    private ExecutorConfig executor = new ExecutorConfig();

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class AdminConfig {

        private String addresses;

    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class ExecutorConfig {

        private String appname;

        private String address;

        private String ip;

        private Integer port;

        private String logpath;

        private Integer logretentiondays;

    }
}
