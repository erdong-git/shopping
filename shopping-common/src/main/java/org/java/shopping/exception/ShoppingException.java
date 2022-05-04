package org.java.shopping.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.java.shopping.enums.ShoppingExceptionEnum;


@AllArgsConstructor //生成带参的构造方法
@Data
public class ShoppingException extends RuntimeException {

    private ShoppingExceptionEnum shoppingenum; //枚举类型的参数


}
