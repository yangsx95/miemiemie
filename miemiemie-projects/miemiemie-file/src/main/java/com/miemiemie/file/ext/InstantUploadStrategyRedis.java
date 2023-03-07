package com.miemiemie.file.ext;

import com.miemiemie.file.FileMetadata;
import com.miemiemie.file.FileObject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

/**
 * 秒传策略-redis
 *
 * @author yangshunxiang
 * @since 2023/3/6
 */
@Slf4j
public class InstantUploadStrategyRedis implements InstantUploadStrategy {

    @Getter
    private final RedisTemplate<String, FileObject> redisTemplate;

    public InstantUploadStrategyRedis(RedisTemplate<String, FileObject> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Optional<FileObject> existAndGet(String key) {

        return Optional.empty();
    }

    @Override
    public void record(String key, FileObject fileObject) {
        try {
            redisTemplate.opsForHash().put(getRedisKey(key, fileObject), fileObject.getPart() + "__" + fileObject.getFilepath(), fileObject);
        } catch (Exception e) {
            log.warn("record file {} error", fileObject.getFileMetadata().get(FileMetadata.FILE_NAME), e);
        }
    }

    @Override
    public void clear(String key, FileObject fileObject) {

    }

    private String getRedisKey(String key, FileObject fileObject) {
        return fileObject.getServerType() + ":" + fileObject.getServerId() + ":instant-upload:" + getKeyFromMetaData(fileObject.getFileMetadata());
    }

}
