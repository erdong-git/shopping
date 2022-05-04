package org.java.shopping.web;

import org.java.shopping.entity.Brand;
import org.java.shopping.service.BrandService;
import org.java.shopping.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     *
     * @return 商品品牌列表的集合
     * @param page  当前页
     * @param limit 分页显示的数量
     */
    @GetMapping("/list")
    public ResponseEntity<PageResult<Brand>> loadBrand(Integer page, Integer limit){

        PageResult<Brand> pageResult = brandService.loadBrand(page,limit);

        return ResponseEntity.status(HttpStatus.OK).body(pageResult);
    }

    /**
     * 删除以后，没有返回结果，但是又状态码返回，判断操作是否成功
     * 通过网关访问该服务地址
     * http://api.shopping.com/api/item/brand/del?id=xxx
     * @param id
     * @return
     */
    @DeleteMapping("/del")
    public ResponseEntity<Void> DelBrandById(Integer id){

        brandService.delBrandById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 添加单个品牌信息
     *通过网关访问该服务地址
     * http://api.shopping.com/api/item/brand/save
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<Void> SaveBrand(Brand brand){

        brandService.saveBrand(brand);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
