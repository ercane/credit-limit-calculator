package com.mree.app.core.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "credit-point-api", url = "https://www.random.org/integers/?num=1&min=0&max=1200&col=1&base=10&format=plain")
public interface CreditPointClient {
    @GetMapping(consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    String getCreditPoint();
}
