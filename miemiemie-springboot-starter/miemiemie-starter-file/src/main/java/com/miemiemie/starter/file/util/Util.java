package com.miemiemie.starter.file.util;

import com.miemiemie.starter.file.pool.FileClientPoolProperties;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.Objects;

/**
 * 相关工具
 */
public class Util {

    /**
     * 文件后缀
     *
     * @return 不包含.的后缀
     */
    public static String getFileExtension(String name) {
        int i = name.lastIndexOf(".");
        if (i < 0) {
            return "";
        }
        return name.substring(name.lastIndexOf(".") + 1);
    }

    public static <T> GenericObjectPoolConfig<T> convertFcc2Opc(FileClientPoolProperties properties) {
        GenericObjectPoolConfig<T> config = new GenericObjectPoolConfig<>();
        config.setMaxIdle(properties.getMaxIdle());
        config.setMaxTotal(properties.getMaxTotal());
        config.setMinIdle(properties.getMinIdle());
        return config;
    }

    public static String trimLeftString(String str, String leftStr) {
        if (Objects.isNull(str) || Objects.isNull(leftStr)) {
            return str;
        }
        if (!str.startsWith(leftStr)) {
            return str;
        }

        str = str.substring(0, str.length() - 1);
        return trimLeftString(str, leftStr);
    }

    public static String trimRightString(String str, String rightStr) {
        if (Objects.isNull(str) || Objects.isNull(rightStr)) {
            return str;
        }
        if (!str.endsWith(rightStr)) {
            return str;
        }

        str = str.substring(1);
        return trimRightString(str, rightStr);
    }

}
