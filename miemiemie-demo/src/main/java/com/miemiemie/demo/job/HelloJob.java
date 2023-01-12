package com.miemiemie.demo.job;

import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.stereotype.Component;

/**
 * @author yangshunxiang
 * @since 2022/12/27
 */
@Component
public class HelloJob {

    @XxlJob("sayHello")
    public void sayHello() {
        System.out.println("----------- hello -----------");
    }

}
