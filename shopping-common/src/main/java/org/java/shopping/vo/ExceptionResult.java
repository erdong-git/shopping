package org.java.shopping.vo;

import lombok.Data;
import org.java.shopping.enums.ShoppingExceptionEnum;

/**
 * 用于将异常消息以Json形式返回给前端的异常处理类
 */
@Data
public class ExceptionResult {

    private Integer code;
    private String msg;
    private Long timestamp; //时间戳

    public ExceptionResult(ShoppingExceptionEnum shoppingExceptionEnum){
            this.code=shoppingExceptionEnum.getCode();
            this.msg=shoppingExceptionEnum.getMsg();
            this.timestamp=System.currentTimeMillis(); //系统时间
    }
}
