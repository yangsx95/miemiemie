package com.miemiemie.validation.validator;

import cn.hutool.core.util.IdcardUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * 各种证件号正则枚举
 *
 * @author yangshunxiang
 * @since 2023/3/9
 */
@AllArgsConstructor
public enum IDNumberRegularExp {

    CN_ID_CARD("中国居民身份证") {
        @Override
        protected boolean doValidate(String idNumber) {
            return IdcardUtil.isValidCard(idNumber);
        }
    },

    TW_ID_CARD("台湾身份证") {
        @Override
        protected boolean doValidate(String idNumber) {
            return IdcardUtil.isValidTWCard(idNumber);
        }
    },

    HK_ID_CARD("香港身份证") {
        @Override
        protected boolean doValidate(String idNumber) {
            return IdcardUtil.isValidHKCard(idNumber);
        }
    },



    ;

    /**
     * 国际名称
     */
    @Getter
    private final String idDesc;


    /**
     * 判断证件号是否合规
     *
     * @param idNumber 证件号码
     * @return true 合法
     */
    public boolean validate(String idNumber) {
        if (Objects.isNull(idNumber) || idNumber.isEmpty()) {
            return false;
        }
        return doValidate(idNumber);
    }

    protected abstract boolean doValidate(String idNumber);

}
