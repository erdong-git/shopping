package org.java.shopping.service;

import org.java.shopping.entity.Sku;
import org.java.shopping.feign.GoodsClient;
import org.java.shopping.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GoodsService {

    @Autowired
    GoodsClient goodsClient;


    public Map goodsDetail(Long id){

        Map<String, Object> map = new HashMap<>();
        Sku sku = goodsClient.loadSkuDetail(id);
//        System.out.println(sku);
        map.put("id",sku.getId());
        map.put("title",sku.getTitle());
        String [] titles = sku.getTitle().split(" ");
        map.put("titles",titles);
        map.put("images",sku.getImages());
        map.put("price",sku.getPrice());
        map.put("createTime",sku.getCreateTime());
        Map<String,Object> specMap = JsonUtils.parseMap(sku.getOwnSpec(),String.class,Object.class);
        map.put("specMap",specMap);
        System.out.println(map);


        return  map;
    }

}
