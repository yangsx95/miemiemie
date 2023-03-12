package com.miemiemie.starter.openapi.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User
 * <p>
 *
 * @author Feathers
 * @since 2018-05-31 15:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    private String name;

    @Schema(example = "20")
    private int age;

}
