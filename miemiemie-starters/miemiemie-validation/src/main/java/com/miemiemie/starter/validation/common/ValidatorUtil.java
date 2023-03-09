package com.miemiemie.starter.validation.common;

import cn.hutool.core.collection.CollUtil;
import com.miemiemie.starter.core.enums.ResultStatusEnum;
import com.miemiemie.starter.core.exception.BizException;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * @author yangshunxiang
 * @since 2023/1/13
 */
public final class ValidatorUtil {

    private ValidatorUtil() throws IllegalAccessException {
        throw new IllegalAccessException("can not instance");
    }

    /**
     * 快速校验对象，如果校验失败，立即抛出异常，返回第一条不通过的信息
     *
     * @param object 目标对象
     * @param clazz  目标对象类型
     * @throws BizException 系统业务异常
     */
    public static <T> void quickValidate(T object, Class<T> clazz) throws BizException {
        //初始化检查器
        try (ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)
                .buildValidatorFactory()) {
            Validator validator = validatorFactory
                    .getValidator();

            //校验对象
            Set<ConstraintViolation<T>> validateSet;
            if (clazz == null) {
                validateSet = validator.validate(object);
            } else {
                validateSet = validator.validate(object, clazz);
            }
            //循环validateSet，获取检查结果
            for (ConstraintViolation<T> validate : validateSet) {
                throw new BizException(ResultStatusEnum.PARAMETER_CHECK_FAIL.getCode(), validate.getMessage());
            }
        }
    }

    /**
     * 校验对象，如果校验失败，抛出异常，返回所有的错误信息
     *
     * @param object 目标对象
     * @param clazz  目标对象类型
     * @throws BizException 系统业务异常
     */
    public static <T> void validate(T object, Class<T> clazz) throws BizException {
        //初始化检查器
        try (ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(false)
                .buildValidatorFactory()) {
            Validator validator = validatorFactory
                    .getValidator();

            //校验对象
            Set<ConstraintViolation<T>> validateSet;
            if (clazz == null) {
                validateSet = validator.validate(object);
            } else {
                validateSet = validator.validate(object, clazz);
            }
            if (CollUtil.isEmpty(validateSet)) {
                return;
            }

            StringBuffer sb = new StringBuffer();
            validateSet.forEach(e -> sb.append(e.getMessage()).append(","));
            throw new BizException(ResultStatusEnum.PARAMETER_CHECK_FAIL.getCode(), sb.toString());
        }
    }

}
