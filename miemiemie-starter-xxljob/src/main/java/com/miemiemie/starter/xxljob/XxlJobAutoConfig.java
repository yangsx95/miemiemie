package com.miemiemie.starter.xxljob;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * xxl-job 自动配置
 *
 * @author yangshunxiang
 * @since 2022/12/19
 */
@Configuration
@ComponentScan(basePackageClasses = XxlJobAutoConfig.class)
@EnableConfigurationProperties(XxlJobConfig.class)
public class XxlJobAutoConfig {

    /**
     * xxl-job执行器，spring boot 环境
     *
     * @param xxlJobConfig xxl-job配置对象
     * @param environment  环境对象
     * @return xxl-job执行器
     */
    @ConditionalOnMissingBean(DiscoveryClient.class)
    @Bean
    public XxlJobSpringExecutor xxlJobSpringBootExecutor(XxlJobConfig xxlJobConfig, Environment environment) {
        return genXxlJobSpringExecutor(xxlJobConfig, environment);
    }

    /**
     * xxl-job执行器，spring-cloud 环境
     * @param discoveryClient 服务发现
     * @param xxlJobConfig xxl-job配置对象
     * @param environment 环境对象
     * @return xxl-job 执行器
     */
    @ConditionalOnBean(DiscoveryClient.class)
    @Bean
    public XxlJobSpringExecutor xxlJobSpringCloudExecutor(DiscoveryClient discoveryClient, XxlJobConfig xxlJobConfig, Environment environment) {
        XxlJobSpringExecutor executor = genXxlJobSpringExecutor(xxlJobConfig, environment);
        // spring cloud 环境下，address配置优先作为注册中心的服务名称，而不是调度中心的url
        // 如果没有找到对应名称的服务，则会作为调度中心的url直接使用
        String addresses = xxlJobConfig.getAdmin().getAddresses();
        String jobServer = getAdminAddressFromDiscovery(discoveryClient, addresses);
        if (StringUtils.hasText(jobServer)) {
            executor.setAdminAddresses(jobServer);
        } else {
            executor.setAdminAddresses(addresses);
        }
        return executor;
    }

    private XxlJobSpringExecutor genXxlJobSpringExecutor(XxlJobConfig xxlJobConfig, Environment environment) {
        XxlJobSpringExecutor executor = new XxlJobSpringExecutor();
        XxlJobConfig.AdminConfig adminConfig = xxlJobConfig.getAdmin();
        XxlJobConfig.ExecutorConfig executorConfig = xxlJobConfig.getExecutor();
        executor.setAccessToken(xxlJobConfig.getAccessToken());
        executor.setAdminAddresses(adminConfig.getAddresses());
        executor.setAddress(executorConfig.getAddress());
        executor.setIp(executorConfig.getIp());
        executor.setPort(executorConfig.getPort());
        // 应用名称默认为程序名称
        if (!StringUtils.hasText(executorConfig.getAppname())) {
            executor.setAppname(environment.getProperty("spring.application.name"));
        } else {
            executor.setAppname(executorConfig.getAppname());
        }
        executor.setLogPath(executorConfig.getLogpath());
        executor.setLogRetentionDays(executorConfig.getLogretentiondays());
        return executor;
    }

    private String getAdminAddressFromDiscovery(DiscoveryClient discoveryClient, String address) {
        if (!StringUtils.hasText(address)) {
            return null;
        }
        final String finalAddress = address.trim();
        List<String> jobServers = discoveryClient.getServices()
                .stream()
                .filter(StringUtils::hasText)
                .filter(s -> s.equals(finalAddress))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(jobServers)) {
            return null;
        }
        return jobServers.get(0);
    }
}
