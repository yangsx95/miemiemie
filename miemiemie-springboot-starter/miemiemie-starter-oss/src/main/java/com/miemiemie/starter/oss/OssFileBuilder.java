package com.miemiemie.starter.oss;

import com.github.f4b6a3.ulid.Ulid;
import com.miemiemie.core.exception.BizException;
import org.springframework.util.StringUtils;

import java.nio.file.Paths;

/**
 * oss文件对象建造器
 *
 * @author yangshunxiang
 * @since 2023/1/12
 */
public class OssFileBuilder {

    private final OssFile ossFile = new OssFile();

    public static OssFileBuilder builder() {
        return new OssFileBuilder();
    }

    public OssFileBuilder bucketName(String bucketName) {
        this.ossFile.setBucketName(bucketName);
        return this;
    }

    public OssFileBuilder appendDir(String dir) {
        String originDir = this.ossFile.getDir();
        this.ossFile.setDir(Paths.get(originDir, dir).toString());
        return this;
    }

    public OssFileBuilder randomDir() {
        String originDir = this.ossFile.getDir();
        this.ossFile.setDir(Paths.get(originDir, Ulid.fast().toString()).toString());
        return this;
    }

    public OssFileBuilder filename(String filename) {
        this.ossFile.setFilename(filename);
        return this;
    }

    public OssFileBuilder randomFilename(String fileExtension) {
        if (!StringUtils.hasText(fileExtension)) {
            this.ossFile.setFilename(Ulid.fast().toString());
            return this;
        }
        fileExtension = fileExtension.trim().replaceAll("^\\.+", "");
        this.ossFile.setFilename(Ulid.fast() + "." + fileExtension);
        return this;
    }

    public OssFile build() {
        if (!StringUtils.hasText(ossFile.getFilename())) {
            throw new BizException("文件名称不存在");
        }
        return ossFile;
    }

}
