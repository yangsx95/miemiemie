package com.miemiemie.starter.xss.filter;

import com.miemiemie.starter.xss.ignore.XssRequestIgnorer;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.owasp.validator.html.Policy;

import java.io.IOException;
import java.util.List;

/**
 * 防XSS攻击过滤器
 *
 * @author yangshunxiang
 * @since 2023/4/2
 */
@Slf4j
public class XssFilter implements Filter {

    private final List<XssRequestIgnorer> xssRequestIgnorerList;

    private final Policy xssPolicy;

    public XssFilter(List<XssRequestIgnorer> xssRequestIgnorerList, Policy xssPolicy) {
        this.xssRequestIgnorerList = xssRequestIgnorerList;
        this.xssPolicy = xssPolicy;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 判断请求是否需要进行xss处理
        boolean isIgnored = xssRequestIgnorerList.stream()
                .anyMatch(xssRequestIgnorer -> xssRequestIgnorer.isIgnoredRequest((HttpServletRequest) request));
        if (isIgnored) {
            log.debug("xss filter ignore request, path: {}", ((HttpServletRequest) request).getRequestURI());
            chain.doFilter(request, response);
            return;
        }

        log.debug("xss filter request path: {}", ((HttpServletRequest) request).getRequestURI());
        chain.doFilter(new XssRequestWrapper((HttpServletRequest) request, xssRequestIgnorerList, xssPolicy), response);
    }
}
