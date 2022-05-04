package org.java.shopping.web;

import org.java.shopping.entity.Sku;
import org.java.shopping.feign.SkuClient;
import org.java.shopping.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SkuClient skuClient;

    @GetMapping("/list/{title}")
    public ResponseEntity<PageResult<Sku>> searchSku(Integer page, Integer limit, @PathVariable("title") String title){

        PageResult<Sku> pageResult = skuClient.getLstByTitle(page,limit,title);

        return ResponseEntity.status(HttpStatus.OK).body(pageResult);
    };
}
