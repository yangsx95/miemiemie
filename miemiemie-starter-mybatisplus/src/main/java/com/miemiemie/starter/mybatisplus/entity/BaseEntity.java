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
     * 创建人
     */
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String createBy;

    /**
     * 创建人名称
     */
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String createName;

    /**
     * 创建人所属组织代码
     */
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String createOrgCode;

    /**
     * 创建时间
     */
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime createTime;

    /**
     * 最后更新人
     */
    @TableField
    private String updateBy;

    /**
     * 最后更新人名称
     */
    @TableField
    private String updateName;

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
