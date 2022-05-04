package org.java.shopping.web;

import org.java.shopping.entity.Sku;
import org.java.shopping.service.SkuService;
import org.java.shopping.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/sku")
public class SkuController {

    @Autowired
    private SkuService skuService;

    /**
     * 无条件查询所有商品信息的的方法
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<PageResult<Sku>> getAll(Integer page,Integer limit){

        PageResult<Sku> pageResult = skuService.loadSku(page, limit);

        return ResponseEntity.status(HttpStatus.OK).body(pageResult);
    }

    /**
     * localhost:8000/sku/searchByTitle/华为?page=1&&limit=5
     * @param page
     * @param limit
     * @param title
     * @return
     */
    @GetMapping("/searchByTitle/{title}")
    public ResponseEntity<PageResult<Sku>> getLstByTitle(Integer page,Integer limit,@PathVariable("title") String title){

        PageResult<Sku> pageResult = skuService.getListByTitle(page,limit,title);

        return ResponseEntity.status(HttpStatus.OK).body(pageResult);
    }

    /**
     * 增加的方法
     * @param sku 商品类
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<Void> saveSku(Sku sku){

        //设置添加与修改时间
        sku.setCreateTime(new Date());
        sku.setLastUpdateTime(new Date());

        skuService.saveSku(sku);


        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 通过id删除的方法
     * @param id 主键
     * @return
     */
    @DeleteMapping("/del")
    public ResponseEntity<Void> delSku(Long id){

        skuService.delSkuById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 通过id查找返回对应的商品
     * @param id
     * @return
     */
    @GetMapping("/loadSkuDetail/{id}")
    public ResponseEntity<Sku> loadSkuDetail(@PathVariable("id") Long id){

        Sku sku = skuService.loadSkuDetail(id);

        return ResponseEntity.status(HttpStatus.OK).body(sku);

    }
}
