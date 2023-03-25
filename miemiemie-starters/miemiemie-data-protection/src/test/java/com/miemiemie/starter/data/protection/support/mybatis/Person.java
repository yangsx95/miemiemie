package com.miemiemie.starter.data.protection.support.mybatis;

import com.miemiemie.starter.data.protection.SensitiveData;
import com.miemiemie.starter.data.protection.strategy.IdCardProtectionStrategy;
import com.miemiemie.starter.mybatisplus.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yangshunxiang
 * @since 2023/3/24
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Person extends BaseEntity {

    private String pname;

    private Integer gender;

    private Integer belief;

    @SensitiveData(strategy = IdCardProtectionStrategy.class)
    private String cardNo;

}