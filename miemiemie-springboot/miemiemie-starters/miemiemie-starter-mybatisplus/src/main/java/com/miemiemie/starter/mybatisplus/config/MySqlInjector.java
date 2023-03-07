package com.miemiemie.starter.mybatisplus.config;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;
import com.miemiemie.starter.mybatisplus.method.UpdateBatchByIdMethod;

import java.util.List;

/**
 * sql注入器
 *
 * @author 杨顺翔
 */
public class MySqlInjector extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        // 批量插入数据，并且指定插入时什么字段需要填充
        methodList.add(new InsertBatchSomeColumn("insertBatch", i -> i.getFieldFill() != FieldFill.UPDATE && !i.isLogicDelete()));
        // 批量更新数据
        methodList.add(new UpdateBatchByIdMethod());
        return methodList;
    }
}
