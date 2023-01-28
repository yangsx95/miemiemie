package com.miemiemie.starter.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.autoconfigure.MybatisPlusPropertiesCustomizer;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.miemiemie.starter.mybatisplus.config.GenericEnumTypeHandler;
import com.miemiemie.starter.mybatisplus.config.MyBatisLogInterceptor;
import com.miemiemie.starter.mybatisplus.config.MyMetaObjectHandler;
import com.miemiemie.starter.mybatisplus.config.MySqlInjector;
import com.miemiemie.starter.mybatisplus.enums.DeletedEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusAutoConfig {

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Bean
    public DbType dbType() {
        return DbType.getDbType(jdbcUrl);
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
        // 单页限制最大1000
        pageInterceptor.setMaxLimit(1000L);
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

    /**
     * 添加字段自动填充处理器
     *
     * @return 字段自动填充处理器
     */
    @Bean
    public MetaObjectHandler myMetaObjectHandler() {
        return new MyMetaObjectHandler();
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
            globalConfig.getDbConfig().setLogicDeleteValue(DeletedEnum.DELETED.getCode().toString());

            MybatisConfiguration configuration = new MybatisConfiguration();
            configuration.setDefaultEnumTypeHandler(GenericEnumTypeHandler.class);
            properties.setConfiguration(configuration);
        };
    }

    @Bean
    MyBatisLogInterceptor myBatisLogInterceptor() {
        return new MyBatisLogInterceptor();
    }
}
