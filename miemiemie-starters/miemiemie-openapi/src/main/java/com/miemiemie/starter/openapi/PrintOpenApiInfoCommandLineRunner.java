package com.miemiemie.starter.openapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author 杨顺翔
 * @since 2024/6/16
 */
@Component
@Slf4j
public class PrintOpenApiInfoCommandLineRunner implements CommandLineRunner {

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Value("${server.port:8080}")
    private int port;

    @Value("${springdoc.swagger-ui.path:/swagger-ui/index.html}")
    private String swaggerPath;

    @Override
    public void run(String... args) {
        String ip;
        try {
             ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            ip = "127.0.0.1";
        }

        log.info("Swagger UI is available at http://{}:{}{}{}", ip, port, contextPath, swaggerPath);
    }


}
