package org.java.shopping.web;


import org.java.shopping.entity.Item;
import org.java.shopping.enums.ShoppingExceptionEnum;
import org.java.shopping.exception.ShoppingException;
import org.java.shopping.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    public ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> saveItem(Item item){

        if(item.getPrice()==null){
            //如果缺失价格，则不能添加
            //抛出异常,使用枚举类型抛出异常状态码与原因
            throw  new ShoppingException(ShoppingExceptionEnum.PRICE_CANNOT_BE_NULL);
        }
        item=itemService.saveItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);


    }


}
    /**
     * 非Restful风格
     * 如果发出请求是item，并且请求是Post方式，会自动调用该方法
     * @param item
     * @return
     */
//    @PostMapping
//    public Item saveItem(Item item){
//        return itemService.saveItem(item);
//    }
//
//    }


