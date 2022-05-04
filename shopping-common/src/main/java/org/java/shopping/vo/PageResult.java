package org.java.shopping.vo;

import lombok.Data;

import java.util.List;

/**
 * 该类用于保存分页查询的结果
 * @param <T>
 */
@Data
public class PageResult<T> {

    private List<T> data;
    private int code; //状态码 0为正常
    private String msg=""; //当无法显示时，提示的错误信息
    private long count; //数据的总条数

    private Integer pageNum; //当前页数
    private Integer maxPage; //最大页数
}
