package com.miemiemie.starter.mybatisplus.config;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.AlwaysUpdateSomeColumnById;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

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
        // 批量插入多条数据，并且指定插入时什么字段需要填充
        methodList.add(new InsertBatchSomeColumn("insertBatch", i -> i.getFieldFill() != FieldFill.UPDATE && !i.isLogicDelete()));
        // 根据id批量更新某些字段，并且更新时不更新策略为INSERT的字段
        methodList.add(new AlwaysUpdateSomeColumnById(i -> i.getFieldFill() != FieldFill.INSERT));
        return methodList;
    }
}
