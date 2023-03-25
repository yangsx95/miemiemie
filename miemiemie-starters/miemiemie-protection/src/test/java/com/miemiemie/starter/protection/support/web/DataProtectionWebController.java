package com.miemiemie.starter.protection.support.web;

import com.miemiemie.starter.protection.annotations.DataProtection;
import com.miemiemie.starter.protection.masking.IdNoMasking;
import com.miemiemie.starter.protection.masking.MobileMasking;
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

        @DataProtection(strategy = IdNoMasking.class)
        private String idCard;

        @DataProtection(strategy = MobileMasking.class)
        private String mobile;

    }

}
