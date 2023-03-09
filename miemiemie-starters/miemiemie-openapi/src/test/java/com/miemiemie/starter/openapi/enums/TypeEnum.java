package com.miemiemie.starter.openapi.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.miemiemie.starter.core.enums.CommonEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TypeEnum implements CommonEnum<Integer, String> {
    HUMAN(1, "人类"),
    DOG(2, "犬类"),
    ;

    @JsonValue
    private final Integer code;

    private final String message;
}
