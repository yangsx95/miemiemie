package com.miemiemie.starter.xss.ignore;

import com.miemiemie.starter.xss.properties.XssProperties;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.AntPathMatcher;

import java.util.List;

/**
 * 用于忽略yaml中配置的忽略规则
 *
 * @author yangshunxiang
 * @since 2023/4/4
 */
public class SpringPropertiesXssRequestIgnorer implements XssRequestIgnorer {

    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

    private final XssProperties xssProperties;

    public SpringPropertiesXssRequestIgnorer(XssProperties xssProperties) {
        this.xssProperties = xssProperties;
    }

    @Override
    public boolean isIgnoredRequest(HttpServletRequest request) {
        List<String> excludeUrlPath = xssProperties.getExcludeUrlPath();
        String requestURI = request.getRequestURI();
        for (String urlPath : excludeUrlPath) {
            if (ANT_PATH_MATCHER.match(urlPath, requestURI)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isIgnoreParam(String paramName, String paramValue) {
        if (xssProperties.getExcludeValue().contains(paramValue)) {
            return true;
        }

        if (xssProperties.getExcludeParameterName().contains(paramName)) {
            return true;
        }
        return xssProperties.getExcludeParameterValue().contains(paramValue);
    }

    @Override
    public boolean isIgnoreHeader(String headerName, String headerValue) {
        if (xssProperties.getExcludeValue().contains(headerValue)) {
            return true;
        }

        if (xssProperties.getExcludeHeaderName().contains(headerName)) {
            return true;
        }
        return xssProperties.getExcludeHeaderValue().contains(headerValue);
    }
}
