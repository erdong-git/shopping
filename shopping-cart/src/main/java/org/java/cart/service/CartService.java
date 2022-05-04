package org.java.cart.service;

import org.apache.commons.lang.StringUtils;
import org.java.auth.pojo.UserInfo;
import org.java.cart.Interceptor.CartInterceptor;
import org.java.cart.Pojo.Cart;
import org.java.cart.feign.ItemClient;
import org.java.shopping.entity.Sku;
import org.java.shopping.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * CartController对应的Service类
 */
@Service
public class CartService {


    @Autowired
    private ItemClient itemClient;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //给储存在Redis的键设定一个前缀
    private final String KEY_PREFIX ="shopping:cart:uid";

    /**
     * 将商品添加至购物车的方法
     */
    public void addCart(Cart cart){
        //从ThreadLocal中获得用户信息
        UserInfo user = CartInterceptor.getUserInfoFromThreadLocal();
        //将前缀与Userid拼接
        String key = KEY_PREFIX+user.getId();
        //根据key从Redis中查找数据，返回的一个是map集合
        BoundHashOperations<String, Object, Object> map = stringRedisTemplate.boundHashOps(key);
        //后续判断需要用到商品的Skuid与num,将其取出
        Long skuId=cart.getSkuId();
        Integer num =cart.getNum();
        //创建一个Boolean变量判断从该集合中查找是否存在本次加入的商品
        Boolean flag = map.hasKey(skuId.toString());
        //判断Boolean变量
        if(flag){
            //如果存在，则需要给商品num+1
            //需要将map里的商品反序列化为对象，然后执行+1操作
            String json =map.get(skuId.toString()).toString();
            cart= JsonUtils.parse(json, Cart.class);
            cart.setNum(cart.getNum()+num);
        }else {
            //商品不存在，调用商品微服务通过skuId查找该商品，将值设置到cart中
            Sku sku = itemClient.loadSkuDetail(skuId);
            cart.setUserId(user.getId());
            cart.setTitle(sku.getTitle());
            cart.setImage(sku.getImages());
            cart.setPrice(sku.getPrice());
        }
        //无论商品是否存在，都将该map返回给Redis
        map.put(skuId.toString(),JsonUtils.serialize(cart));


    }

    /**
     * 对应Controller的query
     * @return
     */
    public List<Cart> query() {
        //取出用户的登录信息
        UserInfo userInfo = CartInterceptor.getUserInfoFromThreadLocal();
        //拼接用户在Redis中的id
        String key =KEY_PREFIX+userInfo.getId();
        //通过Redis查找
        BoundHashOperations<String, Object, Object> map = stringRedisTemplate.boundHashOps(key);
        //取出map中的所有值
        List<Object> values =map.values();
        //将values转换为Cart类型，使用JDK1.8中的方法
        List<Cart> list = values.stream().map(k -> JsonUtils.parse(k.toString(), Cart.class)).collect(Collectors.toList());


        return list;
    }

    /**
     * 对应Controller的updateCart方法
     * @param skuId 商品id
     * @param num  数量
     */
    public void updateCart(Long skuId, Integer num) {
        //获取用户id
        UserInfo userInfo = CartInterceptor.getUserInfoFromThreadLocal();
        //拼接RedisKey
        String key = KEY_PREFIX+userInfo.getId();
        //从Redis中取出map
        BoundHashOperations<String, Object, Object> map = stringRedisTemplate.boundHashOps(key);
        //从map中取出skuId对应的商品
        String goods = map.get(skuId.toString()).toString();
        //将该商品信息反序列化为Cart对象
        Cart cart = JsonUtils.parse(goods, Cart.class);
        //设置该cart的num
        cart.setNum(num);
        //将该cart重新放入map
        map.put(skuId.toString(),JsonUtils.serialize(cart));
    }

    /**
     * 对应ControllerdelCart
     * @param skuId 商品id
     * @return 删除成功则返回true
     */
    public Boolean delCart(Long skuId) {
        //获取用户id
        UserInfo userInfo = CartInterceptor.getUserInfoFromThreadLocal();
        //拼接RedisKey
        String key = KEY_PREFIX+userInfo.getId();
        //从Redis中取出map
        BoundHashOperations<String, Object, Object> map = stringRedisTemplate.boundHashOps(key);
        //根据id从map中删除该商品
        Long rows = map.delete(skuId.toString());

        return rows==1;

    }
}
