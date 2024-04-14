package com.encora.token.controller;

import com.encora.token.controller.client.ClientCredentialRequest;
import com.encora.token.controller.client.TokenResponse;
import com.encora.token.mapper.TokenMapper;
import com.encora.token.service.TokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TokenController {

  TokenService tokenService;
  TokenMapper tokenMapper;

  @PostMapping("/tokens")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<TokenResponse> createToken(@Valid @RequestBody ClientCredentialRequest clientCredentialRequest) {
    return tokenService.createToken(tokenMapper.mapClientCredential(clientCredentialRequest))
            .map(tokenMapper::mapStarshipResponse);
  }


}
