package com.miemiemie.starter.protection.support.mybatis;

import com.miemiemie.starter.mybatisplus.entity.BaseEntity;
import com.miemiemie.starter.protection.annotations.DataProtection;
import com.miemiemie.starter.protection.masking.IdNoMasking;
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

    @DataProtection(strategy = IdNoMasking.class)
    private String cardNo;

}