package com.encora.ibk.thirdparty;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;


@ReactiveFeignClient(
        name = "movement-client",
        url = "${movement-client.urls.base-url}"
)
public interface MovementClient {
    @GetMapping(value = "${movement-client.urls.get-movement-url}", headers = {"Content-Type=application/json"})
    Mono<String> getMovements(@RequestHeader("Authorization") String token);

}
