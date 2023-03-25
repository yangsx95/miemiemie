package com.miemiemie.starter.protection.masking;

/**
 * 银行卡号脱敏
 *
 * @author yangshunxiang
 * @since 2023/3/25
 */
public class BankCardNoMasking extends AbstractStringMasking {

    @Override
    protected String doMask(String bankCardNo) {
        // 银行卡号正则表达式
        String regex = "(?<=\\w{4})\\w(?=\\w{4})";
        String replacement = "*";
        return bankCardNo.replaceAll(regex, replacement);
    }

}
