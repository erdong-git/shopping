package org.java.shopping.enums;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 封装异常信息的枚举类
 */

@AllArgsConstructor
@Getter

public enum ShoppingExceptionEnum {

    //此处相当于带参的构造方法
    USER_ADD_ERROR(404,"用户添加失败"),
    SKU_REMOVE_ERROR(404,"商品删除失败"),
    SKU_ADD_ERROR(404,"商品添加失败"),
    SKU_LIST_NOT_FOUND(404,"找不到商品列表"),
    BRAND_ADD_ERROR(404,"品牌添加失败"),
    BRAND_REMOVE_ERROR(404,"品牌删除失败"),
    BRAND_LIST_NOT_FOUND(404,"品牌列表不存在"),
    CATEGORY_LIST_NOT_FOUND(404,"没有找到指定的商品类别"),
    NAME_CANNOT_BE_NULL(400,"商品名称不允许为空"),
    PRICE_CANNOT_BE_NULL(400,"商品价格不允许为空");

    private Integer code; //状态码
    private String msg; //异常原因
}
