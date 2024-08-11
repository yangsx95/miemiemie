package com.miemiemie.starter.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.miemiemie.starter.mybatisplus.enums.DeletedEnum;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 业务基础实体
 */
@Data
@FieldNameConstants
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 4935519255380622171L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 是否删除
     */
    @TableField
    @TableLogic
    private DeletedEnum deleted;

    /**
     * 创建时间
     */
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @TableField(updateStrategy = FieldStrategy.NEVER, fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 更新时间
     */
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long updateBy;

}
