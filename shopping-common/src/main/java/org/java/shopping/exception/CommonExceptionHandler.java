package org.java.shopping.exception;

import org.java.shopping.enums.ShoppingExceptionEnum;
import org.java.shopping.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice //全局异常处理
public class CommonExceptionHandler {


    /**
     * 该方法处理服务中产生的运行时异常
     * @param ex
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResult> handleException(ShoppingException ex){
        //此处不可以将状态码写成一个固定值
        //将异常的状态码与原因，封装成一个枚举类型
        ShoppingExceptionEnum shoppingenum = ex.getShoppingenum();
        //返回状态码并告知错误原因
            return ResponseEntity.status(shoppingenum.getCode()).body(new ExceptionResult(shoppingenum));

    }
}
