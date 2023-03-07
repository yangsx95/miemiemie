package com.miemiemie.validation.validator;

import com.miemiemie.validation.annotation.FieldMatch;
import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * FieldMatch字段验证策略
 *
 * @author yangshunxiang
 * @see FieldMatch
 * @since 2023/01/27
 */
@FunctionalInterface
public interface FieldMatchStrategy {

    /**
     * 校验字段
     *
     * @param fieldsValues 字段值列表
     * @return 校验结果
     */
    boolean valid(Object... fieldsValues);

    /**
     * 所有字段值必须相同
     */
    class AllEqualStrategy implements FieldMatchStrategy {

        @Override
        public boolean valid(Object... fieldsValues) {
            if (fieldsValues == null || fieldsValues.length < 2) {
                return true;
            }
            Object cur = fieldsValues[0];
            for (int i = 1; i < fieldsValues.length; i++) {
                if (Objects.equals(fieldsValues[i], cur)) {
                    cur = fieldsValues[i];
                    continue;
                }
                return false;
            }
            return true;
        }
    }

    /**
     * 所有字段值不相同
     */
    class AllNotEqualStrategy implements FieldMatchStrategy {

        @Override
        public boolean valid(Object... fieldsValues) {
            if (fieldsValues == null || fieldsValues.length < 2) {
                return false;
            }
            for (int i = 0; i < fieldsValues.length; i++) {
                for (int j = i + 1; j < fieldsValues.length; j++) {
                    if (Objects.equals(fieldsValues[i], fieldsValues[j])) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    /**
     * 至少有一个字段不为null
     */
    class AtLeastOneNotNull implements FieldMatchStrategy {
        @Override
        public boolean valid(Object... fieldsValues) {
            boolean pass = false;
            for (Object value : fieldsValues) {
                pass = pass || (value != null);
            }
            return pass;
        }
    }

    /**
     * 至少有一个字段不为empty。支持的类型有：CharSequence、Array、Collection、Map
     */
    class AtLeastOneNotEmpty implements FieldMatchStrategy {

        @Override
        public boolean valid(Object... fieldsValues) {
            boolean pass = false;
            for (Object value : fieldsValues) {
                pass = pass || isNotEmpty(value);
            }
            return pass;
        }

        private boolean isNotEmpty(Object value) {
            if (value == null) {
                return false;
            }
            if (value instanceof CharSequence) {
                return StringUtils.hasLength((CharSequence) value);
            } else if (value instanceof Collection) {
                return ((Collection<?>) value).size() > 0;
            } else if (value instanceof Map) {
                return ((Map<?, ?>) value).isEmpty();
            } else if (value.getClass().isArray()) {
                return Array.getLength(value) > 0;
            }
            return true;
        }
    }

    /**
     * 至少有一个字段不为blank。支持的类型有：CharSequence
     */
    class AtLeastOneNotBlank implements FieldMatchStrategy {

        @Override
        public boolean valid(Object... fieldsValues) {
            boolean pass = false;
            for (Object value : fieldsValues) {
                pass = pass || isNotBlank(value);
            }
            return pass;
        }

        private boolean isNotBlank(Object value) {
            if (value == null) {
                return false;
            }
            if (value instanceof CharSequence) {
                return StringUtils.hasText((CharSequence) value);
            }
            return true;
        }
    }
}
