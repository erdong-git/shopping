package org.java.shopping.service;

import org.java.shopping.entity.Item;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class ItemService {

    public Item saveItem(Item item){
        //生成编号
        Integer id =new Random().nextInt(100);
        item.setId(id);
        System.out.println("新增成功");
        return item;
    }

}
