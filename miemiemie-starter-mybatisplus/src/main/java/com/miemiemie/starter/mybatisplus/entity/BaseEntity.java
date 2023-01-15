package com.miemiemie.starter.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 业务基础实体
 */
@Data
@FieldNameConstants
public abstract class BaseEntity implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 是否删除，0：未删除 1：已删除
     */
    @TableField
    @TableLogic
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField
    private LocalDateTime updateTime;

    /**
     * 乐观锁
     */
    @Version
    @TableField
    private Integer version;

}
