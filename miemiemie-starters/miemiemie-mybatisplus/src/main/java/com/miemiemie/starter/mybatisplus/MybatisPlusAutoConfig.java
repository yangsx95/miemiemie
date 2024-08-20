package com.miemiemie.starter.mybatisplus;

import cn.hutool.db.ds.pooled.DbConfig;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.miemiemie.starter.mybatisplus.config.GenericEnumTypeHandler;
import com.miemiemie.starter.mybatisplus.config.MySqlInjector;
import com.miemiemie.starter.mybatisplus.enums.DeletedEnum;
import com.miemiemie.starter.mybatisplus.page.MybatisPlusPageConvert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AutoConfiguration
public class MybatisPlusAutoConfig {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Bean
    public DbType dbType() {
        return DbType.getDbType(extractDatabaseType(jdbcUrl));
    }

    private String extractDatabaseType(String jdbcUrl) {
        Pattern pattern = Pattern.compile("jdbc:(\\w+):");
        Matcher matcher = pattern.matcher(jdbcUrl);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return "";
        }
    }

    /**
     * mybatis plus sql注入器
     *
     * @return sql注入器
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new MySqlInjector();
    }

    /**
     * 添加分页拦截处理器
     *
     * @return 分页拦截处理器
     */
    @Bean
    public PaginationInnerInterceptor paginationInnerInterceptor(DbType dbType) {
        // 分页配置
        PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor();
        // 页溢出不进行处理
        pageInterceptor.setOverflow(Boolean.FALSE);
        // 单页不做限制
        pageInterceptor.setMaxLimit(-1L);
        pageInterceptor.setDbType(dbType);
        return pageInterceptor;
    }

    /**
     * 添加乐观锁拦截处理器
     *
     * @return 乐观锁拦截处理器
     */
    @Bean
    public OptimisticLockerInnerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    @Bean
    public MybatisPlusPropertiesCustomizer mybatisPlusPropertiesCustomizer() {
        return properties -> {
            GlobalConfig globalConfig = properties.getGlobalConfig();

            globalConfig.setBanner(false);

            globalConfig.getDbConfig().setInsertStrategy(FieldStrategy.NOT_EMPTY);
            globalConfig.getDbConfig().setUpdateStrategy(FieldStrategy.NOT_EMPTY);
            globalConfig.getDbConfig().setWhereStrategy(FieldStrategy.NOT_EMPTY);
            globalConfig.getDbConfig().setLogicNotDeleteValue(DeletedEnum.NOT_DELETED.getCode().toString());
            globalConfig.getDbConfig().setLogicDeleteValue(Optional.of(DeletedEnum.DELETED)
                    .map(DeletedEnum::getCode)
                    .map(Object::toString)
                    .orElse("null")
            );

            MybatisPlusProperties.CoreConfiguration configuration = new MybatisPlusProperties.CoreConfiguration();
            configuration.setDefaultEnumTypeHandler(GenericEnumTypeHandler.class);
            configuration.setLogImpl(org.apache.ibatis.logging.stdout.StdOutImpl.class);
            properties.setConfiguration(configuration);
        };
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(PaginationInnerInterceptor paginationInnerInterceptor,
                                                         OptimisticLockerInnerInterceptor optimisticLockerInnerInterceptor) {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.setInterceptors(List.of(paginationInnerInterceptor, optimisticLockerInnerInterceptor));
        return mybatisPlusInterceptor;
    }

    @Bean
    public MybatisPlusPageConvert mybatisPlusPageConvert() {
        return new MybatisPlusPageConvert();
    }

}
