package com.miemiemie.starter.openapi;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.service.OpenAPIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author 杨顺翔
 * @since 2024/6/16
 */
@Slf4j
public class PrintOpenApiInfoCommandLineRunner implements CommandLineRunner {

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Value("${server.port:8080}")
    private int port;

    @Value("${springdoc.swagger-ui.path:/swagger-ui/index.html}")
    private String swaggerPath;

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) {
        try {
            applicationContext.getBean(OpenAPIService.class);

            String ip;
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                ip = "127.0.0.1";
            }

            log.info("[MIEMIEMIE] Swagger UI is available at http://{}:{}{}{}", ip, port, contextPath, swaggerPath);
        } catch (Exception e) {
            log.info("Swagger UI not enable");
        }
    }


}
