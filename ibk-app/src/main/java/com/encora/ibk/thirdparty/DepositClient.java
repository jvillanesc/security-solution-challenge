package com.encora.ibk.thirdparty;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;


@ReactiveFeignClient(
        name = "deposit-client",
        url = "${deposit-client.urls.base-url}"
)
public interface DepositClient {
    @PostMapping(value = "${deposit-client.urls.post-deposit-url}", headers = {"Content-Type=application/json"})
    Mono<String> deposit(@RequestHeader("Authorization") String token);

}
