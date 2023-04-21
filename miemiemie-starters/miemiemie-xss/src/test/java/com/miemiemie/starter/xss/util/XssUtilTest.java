package com.miemiemie.starter.xss.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.owasp.validator.html.Policy;

import java.io.InputStream;
import java.net.URL;

public class XssUtilTest {

    @Test
    public void testScript() throws Exception {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("antisamy.xml");
        Assertions.assertNotNull(is, "文件不存在");
        Policy instance = Policy.getInstance(is);
        String result = XssUtil.cleanXss("<h1>alert('XSS')</h1>", instance);
        System.out.println("----------------" + result);
    }

}
