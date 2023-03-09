package com.miemiemie.starter.web.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.miemiemie.starter.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Configuration
@EnableAutoConfiguration
@SpringBootTest(classes = ObjectMapperTest.class)
public class ObjectMapperTest {

    @Resource
    private ObjectMapper objectMapper;

    @Test
    public void setup() {
    }

    @Test
    public void testSerialize() throws Exception {
        O o = new O();
        o.setAge(18);
        o.setDate(new Date());
        o.setLocalDate(LocalDate.now());
        o.setLocalTime(LocalTime.now());
        o.setLocalDateTime(LocalDateTime.now());
        o.setG(G.A);

        byte[] bytes = objectMapper.writeValueAsBytes(o);
        System.out.println(new String(bytes));
    }

    @Test
    public void testDeserialize() throws Exception {
        String json = "{\"name\":null,\"age\":18,\"date\":\"2023-01-26 10:46:25\",\"localDate\":\"2023-01-26\",\"localTime\":\"10:46:25\",\"localDateTime\":\"2023-01-26 10:46:25\",\"g\":1}\n";
        O o = objectMapper.readValue(json, O.class);
        System.out.println(o);
    }

    @Data
    static class O {

        private String name;

        private Integer age;

        private Date date;

        private LocalDate localDate;

        private LocalTime localTime;

        private LocalDateTime localDateTime;

        @JsonDeserialize(using = CommonEnumDeserializer.class)
        private G g;

    }

    @AllArgsConstructor
    @Getter
     enum G implements CommonEnum<Integer, String> {
        A(1, "a"),
        B(2, "b"),
        ;
        private final Integer code;

        private final String message;
    }


}
