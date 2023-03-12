package com.miemiemie.starter.data.protection;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yangshunxiang
 * @since 2023/3/12
 */
@ConfigurationProperties(prefix = "miemiemie.data.protection")
@Data
public class DataProtectionProperties {

}
