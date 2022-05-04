package org.java.cart.Pojo;

import lombok.Data;

/**
 * 购物车的实体类
 * 用于封装一条购物记录
 */
@Data
public class Cart {

    private Long userId;//用户ID
    private Long skuId;//商品ID
    private String title;//商品标题
    private String image;//商品图片
    private Long price; //商品价格 单位：分
    private Integer num; //商品购买数量
}
