package com.miemiemie.starter.core;

import com.miemiemie.starter.core.page.PageConvertFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PageConvertFactory.class)
public class CoreAutoConfig {
}
