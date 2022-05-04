package org.java.cart.feign;

import org.java.shopping.api.SkuApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Feign：
 * 调用Item-service
 */
@FeignClient(name="item-service")
public interface ItemClient extends SkuApi {
}
