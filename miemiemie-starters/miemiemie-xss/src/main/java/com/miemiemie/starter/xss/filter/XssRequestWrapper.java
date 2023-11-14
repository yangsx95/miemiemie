package com.miemiemie.starter.xss.filter;

import com.miemiemie.starter.xss.ignore.XssRequestIgnorer;
import com.miemiemie.starter.xss.util.XssUtil;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.owasp.validator.html.Policy;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 防XSS攻击请求包装类
 *
 * @author yangshunxiang
 * @since 2023/4/2
 */
@Slf4j
public class XssRequestWrapper extends HttpServletRequestWrapper {

    private final List<XssRequestIgnorer> xssRequestIgnorers;

    private final Policy xssPolicy;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request            the {@link HttpServletRequest} to be wrapped.
     * @param xssRequestIgnorers 忽略器
     * @throws IllegalArgumentException if the request is null
     */
    public XssRequestWrapper(HttpServletRequest request,
                             List<XssRequestIgnorer> xssRequestIgnorers,
                             Policy xssPolicy
    ) {
        super(request);
        this.xssRequestIgnorers = xssRequestIgnorers;
        this.xssPolicy = xssPolicy;
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        final Enumeration<String> headerValues = super.getHeaders(name);

        final List<String> headerValuesList = new ArrayList<>();
        xssRequestIgnorers.forEach(ignorer -> {
            while (headerValues.hasMoreElements()) {
                String v = headerValues.nextElement();
                boolean ignoreHeader = ignorer.isIgnoreHeader(name, v);
                if (!ignoreHeader) {
                    String cleanableValue = XssUtil.cleanXss(v, xssPolicy);
                    headerValuesList.add(cleanableValue);
                }
            }
        });
        return Collections.enumeration(headerValuesList);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterMap = super.getParameterMap();
        xssRequestIgnorers.forEach(ignorer -> parameterMap.forEach((pn, pv) -> {
            String[] cleanableValue = new String[pv.length];
            for (int i = 0; i < pv.length; i++) {
                String v = pv[i];
                boolean ignoreParam = ignorer.isIgnoreParam(pn, v);
                if (ignoreParam) {
                    cleanableValue[i] = v;
                } else {
                    cleanableValue[i] = XssUtil.cleanXss(v, xssPolicy);
                }
            }
            parameterMap.put(pn, cleanableValue);
        }));
        return parameterMap;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream bais = new ByteArrayInputStream(inputHandlers(super.getInputStream()).getBytes());

        return new ServletInputStream() {

            @Override
            public int read() {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

    public String inputHandlers(ServletInputStream servletInputStream) {
        StringBuilder sb = new StringBuilder();

        try (servletInputStream;
             BufferedReader reader = new BufferedReader(new InputStreamReader(servletInputStream, StandardCharsets.UTF_8))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("xss防护出现异常", e);
        }
        return Optional.ofNullable(XssUtil.cleanXss(sb.toString(), xssPolicy)).orElse("");
    }


}
