package org.java.shopping.feign;

import org.java.shopping.api.SkuApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="item-service")
public interface GoodsClient extends SkuApi {
}
