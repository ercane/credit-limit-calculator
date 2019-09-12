package com.mree.app.core.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "credit-point-api", url = "${credit-point-api.url}")
public interface CreditPointClient {
    @GetMapping(consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    String getCreditPoint();
}
