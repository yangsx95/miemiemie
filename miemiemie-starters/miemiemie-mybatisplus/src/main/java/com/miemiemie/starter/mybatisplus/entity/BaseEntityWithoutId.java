package com.miemiemie.starter.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.miemiemie.starter.mybatisplus.enums.DeletedEnum;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author yangshunxiang
 * @since 2024/8/14
 */
@Data
@FieldNameConstants
public class BaseEntityWithoutId implements Serializable {

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
