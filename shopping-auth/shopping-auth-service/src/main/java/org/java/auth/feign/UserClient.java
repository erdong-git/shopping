package org.java.auth.feign;

import org.java.shopping.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name="user-service")
public interface UserClient extends UserApi {
}
