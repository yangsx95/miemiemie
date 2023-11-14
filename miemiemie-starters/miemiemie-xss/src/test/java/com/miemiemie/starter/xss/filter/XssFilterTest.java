package com.miemiemie.starter.xss.filter;

import com.miemiemie.starter.xss.XssAutoConfiguration;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = XssFilterTest.Conf.class)
@AutoConfigureMockMvc
@Import(XssAutoConfiguration.class)
public class XssFilterTest {

    @EnableAutoConfiguration
    @Configuration
    @ComponentScan(basePackageClasses = XssFilterTest.Conf.class)
    public static class Conf {
    }

    @Controller
    @RequestMapping("/test")
    public static class XssTestController {

        @PostMapping(value = "/postString", produces = "text/plain", consumes = "text/plain")
        @ResponseBody
        public String postString(@RequestBody String input) {
            return input;
        }

    }

    @Resource
    private MockMvc mockMvc;

    @Test
    public void testFilter() throws Exception {
        String input = "<h2>alert('xss')</h2>";
        String expectedOutput = "alert('xss')";

        mockMvc.perform(MockMvcRequestBuilders.post("/test/postString")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(input))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedOutput));
    }

}
