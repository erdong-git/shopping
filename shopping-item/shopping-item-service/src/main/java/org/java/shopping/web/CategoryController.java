package org.java.shopping.web;

import org.java.shopping.entity.Category;
import org.java.shopping.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * 通过@RequestParam取到Pid，然后查询商品的父类信息
     * 在Restful风格中需要返回状态码，因此不应该直接返回list集合，而应该使用ResponseEntity来返回数据，
     * ResponseEntity可以携带一个状态码与body，body里存放数据
     * @param pid
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<List<Category>> findCategoryByPid(@RequestParam("pid") Integer pid){

        //获取商品类型List
        List<Category> categoryList =categoryService.selectByPid(pid) ;


        return  ResponseEntity.status(HttpStatus.OK).body(categoryList);
    }

    /**
     * 查询所有
     * @param
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<List<Category>> findCategoryAll(){

        //获取商品类型List
        List<Category> categoryList =categoryService.selectAll();


        return  ResponseEntity.status(HttpStatus.OK).body(categoryList);
    }

}
