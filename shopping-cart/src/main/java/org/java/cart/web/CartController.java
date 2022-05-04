package org.java.cart.web;

import org.java.cart.Pojo.Cart;
import org.java.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 该控制器类用于控制购物车的方法
 */
@RestController
public class CartController {


    @Autowired
    private CartService cartService;

    /***
     * 将购物车内容添加至Redis的方法
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){
        System.out.println("进入了add方法");
        cartService.addCart(cart);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /***
     * 根据用户的id从Redis中查询他的购物车信息
     * @return
     */
    @GetMapping("/query")
    public ResponseEntity<List<Cart>> query(){

        //执行Service方法
        List<Cart> list = cartService.query();
        //判断是否为空
        if(list.isEmpty()){
            //用户没有购物车列表
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return  ResponseEntity.ok(list);
    }

    /**
     * 修改用户购物车中商品的数量
     * @param skuId 商品id
     * @param num 商品数量
     * @return
     */
    @PutMapping("/update/{skuId}/{num}")
    public ResponseEntity<Void> updateCart(@PathVariable Long skuId,
                                           @PathVariable Integer num){

        cartService.updateCart(skuId,num);

        return ResponseEntity.ok().build();
    }

    /**
     * 根据skuid删除一个商品
     * @param skuId
     * @return
     */
    @DeleteMapping("/del/{skuId}")
    public ResponseEntity<Void> delCart(@PathVariable Long skuId){

        Boolean flag = cartService.delCart(skuId);

        if(flag){
            //删除成功
            return ResponseEntity.ok().build();
        }else{
            //删除失败
            return ResponseEntity.badRequest().build();
        }

    }
}
