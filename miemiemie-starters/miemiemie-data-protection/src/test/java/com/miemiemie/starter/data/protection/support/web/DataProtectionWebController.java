package com.miemiemie.starter.data.protection.support.web;

import com.miemiemie.starter.data.protection.DataProtection;
import com.miemiemie.starter.data.protection.strategy.IdCardProtectionStrategy;
import com.miemiemie.starter.data.protection.strategy.MobileProtectionStrategy;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangshunxiang
 * @since 2023/3/14
 */
@RestController
@RequestMapping("/dataprotection/")
public class DataProtectionWebController {

    @GetMapping("/get")
    public UserInfo get() {
        return new UserInfo("zhangsan", "320305199509090000", "18364567823");
    }

    @Data
    @AllArgsConstructor
    public static class UserInfo {

        private String username;

        @DataProtection(strategy = IdCardProtectionStrategy.class)
        private String idCard;

        @DataProtection(strategy = MobileProtectionStrategy.class)
        private String mobile;

    }

}
