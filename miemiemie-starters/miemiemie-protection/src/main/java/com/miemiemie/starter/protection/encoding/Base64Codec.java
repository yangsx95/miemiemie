package com.miemiemie.starter.protection.encoding;

import cn.hutool.core.codec.Base64;

/**
 * @author yangshunxiang
 * @since 2023/3/25
 */
public class Base64Codec implements Codec {

    /**
     * 在76个char之后是CRLF还是EOF
     */
    private boolean isMultiLine;

    /**
     * 是否使用URL安全字符，在URL Safe模式下，=为URL中的关键字符，不需要补充。空余的byte位要去掉，一般为false
     */
    private boolean urlSafe;

    public Base64Codec() {
    }

    public Base64Codec(boolean isMultiLine, boolean urlSafe) {
        this.isMultiLine = isMultiLine;
        this.urlSafe = urlSafe;
    }

    @Override
    public byte[] encode(byte[] data) {
        return Base64.encode(data, isMultiLine, urlSafe);
    }

    @Override
    public byte[] decode(byte[] encodedData) {
        return Base64.decode(encodedData);
    }
}
