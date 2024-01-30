package com.miemiemie.starter.feign;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import static feign.Util.decodeOrDefault;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * feign日志处理
 *
 * @author yangshunxiang
 */
@Slf4j
public class FeignLogger extends Logger {

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        Request request = response.request();
        String requestMsg = request.httpMethod().name() + " " + request.url() + " HTTP/1.1";

        boolean hasReqBody = request.body() != null;
        String bodyMsg = hasReqBody ? new String(request.body()) : "";

        String responseMsg = "";
        int status = response.status();

        boolean hasResBody = response.body() != null && !(status == 204 || status == 205);
        if (hasResBody) {
            byte[] bodyData = Util.toByteArray(response.body().asInputStream());
            //noinspection resource
            response = response.toBuilder().body(bodyData).build();
            responseMsg = decodeOrDefault(bodyData, UTF_8, "");
        }
        log(configKey, "request【%s】， request headers 【%s】 body【%s】, response【%s】", requestMsg, request.headers(), bodyMsg, responseMsg);
        return response;
    }

    @Override
    protected void log(String configKey, String format, Object... args) {
        log.info(methodTag(configKey) + String.format(format, args));
    }

}
