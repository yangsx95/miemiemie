package com.miemiemie.starter.excel.annotation;

import java.lang.annotation.*;

/**
 * 请求响应为excel文件流对象
 *
 * @author yangshunxiang
 * @since 2023/1/19
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseExcel {

    /**
     * 响应的文件名，带后缀
     * @return 比如 新建Excel.xls
     */
    String filename() default "";

}
