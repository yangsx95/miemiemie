package com.miemiemie.starter.core;

import com.miemiemie.starter.core.page.PageConvertFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

@AutoConfiguration
@Import(PageConvertFactory.class)
public class CoreAutoConfig {
}
