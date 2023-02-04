package com.miemiemie.starter.swagger.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class User {
    @ApiModelProperty(required = true)
    private Long id;

    @ApiModelProperty
    private String name;

    @ApiModelProperty(required = false, example = "20")
    private int age;

}
