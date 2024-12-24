package com.github.JLQusername.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("product-service")
public interface ProductClient {
    @PostMapping("product/level")
    int getLevel(@RequestParam Integer productId);
}
