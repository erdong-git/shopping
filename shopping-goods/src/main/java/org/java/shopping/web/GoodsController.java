package org.java.shopping.web;


import org.java.shopping.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/item/{id}.html")
    public String goodsDetail(@PathVariable("id") Long id,Model model){
        System.out.println("返回的商品id是"+id);
        Map map=goodsService.goodsDetail(id);
        model.addAllAttributes(map);
        return "/item";
    }


}
