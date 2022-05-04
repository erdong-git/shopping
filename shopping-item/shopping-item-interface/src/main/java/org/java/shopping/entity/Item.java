package org.java.shopping.entity;

import lombok.Data;

import java.util.PrimitiveIterator;

/**
 * 测试类,用于模拟测试添加商品
 */

@Data
public class Item {
    private Integer id;
    private String name;
    private Integer price;
}
