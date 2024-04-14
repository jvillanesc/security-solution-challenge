package com.encora.ibk.thirdparty;

import com.encora.ibk.thirdparty.client.ClientCredentialRequest;
import com.encora.ibk.thirdparty.client.TokenClientResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;


@ReactiveFeignClient(
        name = "token-client",
        url = "${token-client.urls.base-url}"
)
public interface TokenClient {
    @PostMapping(value = "${token-client.urls.post-token-url}", headers = {"Content-Type=application/json"})
    Mono<TokenClientResponse> getToken(@RequestBody ClientCredentialRequest clientCredentialRequest);

}
