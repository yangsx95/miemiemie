package com.miemiemie.validation.common;

import javax.validation.groups.Default;

/**
 * @author yangshunxiang
 * @since 2023/1/18
 */
public interface CommonValidatorGroup {

    /**
     * 新增校验组
     */
    interface Add extends Default {
    }

    /**
     * 更新校验组
     */
    interface Update extends Default {
    }

    /**
     * 删除校验组
     */
    interface Delete extends Default {
    }

    /**
     * 通用单挑查询校验组
     */
    interface SelectOne extends Default {
    }

    /**
     * 通用多条查询校验组
     */
    interface SelectList extends Default {
    }

    /**
     * 有效数据查询校验组
     */
    interface SelectEnableList extends Default {
    }
}
