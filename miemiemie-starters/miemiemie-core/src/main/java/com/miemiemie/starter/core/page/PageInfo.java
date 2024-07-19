package com.miemiemie.starter.core.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author 杨顺翔
 * @since 2024/6/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = -6613393368753454508L;

    /**
     * 每页条数
     */
    private long pageSize = 10;

    /**
     * 当前页数
     */
    private long currentPage;
}
