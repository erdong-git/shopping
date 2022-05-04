package org.java.shopping.api;


import org.java.shopping.vo.PageResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.java.shopping.entity.Sku;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 对外暴露的接口，通过访问设定好的GetMapping，来达到访问Service里的Controllerd的目的。
 * 接口的方法的参数类型，数量，方法名称需要与Controller里的一致
 */
public interface SkuApi {

    //获取商品的全部列表的接口
    @GetMapping("sku/list")
    public PageResult<Sku> getAll(@RequestParam("page") Integer page,
                                  @RequestParam("limit") Integer limit);

    @GetMapping("sku/searchByTitle/{title}")
    public PageResult<Sku> getLstByTitle(@RequestParam("page") Integer page,
                                         @RequestParam("limit") Integer limit,
                                         @PathVariable("title") @RequestParam("title") String title);

    @GetMapping("sku/loadSkuDetail/{id}")
    public Sku loadSkuDetail(@PathVariable("id") @RequestParam("id") Long id);
}

