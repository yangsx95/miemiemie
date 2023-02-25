package com.miemiemie.starter.file.util;

import com.miemiemie.starter.file.pool.FileClientPoolProperties;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

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

    @Nullable
    public static String trimLeftString(@Nullable String str, @Nullable String leftStr) {
        if (Objects.isNull(str) || Objects.isNull(leftStr)) {
            return str;
        }
        if (!str.startsWith(leftStr)) {
            return str;
        }

        str = str.substring(0, str.length() - 1);
        return trimLeftString(str, leftStr);
    }

    @Nullable
    public static String trimRightString(@Nullable String str, @Nullable String rightStr) {
        if (Objects.isNull(str) || Objects.isNull(rightStr)) {
            return str;
        }
        if (!str.endsWith(rightStr)) {
            return str;
        }

        str = str.substring(1);
        return trimRightString(str, rightStr);
    }

    public static String getAbsFilepath(String part, String filepath) {
        part = part.replace("\\", "/").trim();
        part = Util.trimLeftString(part, "/");
        part = Util.trimRightString(part, "/");
        filepath = filepath.replace("\\", "/").trim();
        filepath = Util.trimLeftString(filepath, "/");
        filepath = Util.trimRightString(filepath, "/");
        if (!StringUtils.hasText(part)) {
            return "/" + filepath;
        }
        return "/" + part + "/" + filepath;
    }

    public static String getFileDirPath(String part, String filepath) {
        String path = getAbsFilepath(part, filepath);
        if (!path.contains("/")) {
            return "/";
        }
        return path.substring(0, path.lastIndexOf("/"));
    }

    public static String getFilename(String part, String filepath) {
        String path = getAbsFilepath(part, filepath);
        return path.substring(path.lastIndexOf("/") + 1);
    }

}
