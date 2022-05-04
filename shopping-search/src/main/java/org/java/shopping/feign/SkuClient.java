package org.java.shopping.feign;
import org.java.shopping.api.SkuApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="item-service")
public interface SkuClient extends SkuApi {

//    @GetMapping("/sku/searchByTitle/{title}")
//    public PageResult<Sku> searchSku(@RequestParam("page") Integer page,
//                                     @RequestParam("limit") Integer limit,
//                                     @PathVariable("title") @RequestParam("title")String title);
}
