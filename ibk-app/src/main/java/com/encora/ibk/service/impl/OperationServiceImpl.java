package com.encora.ibk.service.impl;

import com.encora.ibk.service.OperationService;
import com.encora.ibk.thirdparty.DepositClient;
import com.encora.ibk.thirdparty.MovementClient;
import com.encora.ibk.thirdparty.TokenClient;
import com.encora.ibk.thirdparty.client.ClientCredentialRequest;
import com.encora.ibk.thirdparty.client.TokenClientResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

  private final TokenClient tokenClient;
  private final DepositClient depositClient;
  private final MovementClient movementClient;
  private final ReactiveRedisTemplate<String, TokenClientResponse> reactiveRedisTemplate;

  @Value("${client-credentials.deposit-service.client-id}")
  private String depositServiceClientId;
  @Value("${client-credentials.deposit-service.password}")
  private String depositServicePassword;
  @Value("${client-credentials.movement-service.client-id}")
  private String movementServiceClientId;
  @Value("${client-credentials.movement-service.password}")
  private String movementServicePassword;

  public Mono<String> deposit() {
    return getToken(depositServiceClientId, depositServicePassword)
            .flatMap(tokenClientResponse -> depositClient.deposit(tokenClientResponse.getAccessToken()));
  }

  @Override
  public Mono<String> getMovements() {
    return getToken(movementServiceClientId, movementServicePassword)
            .flatMap(tokenClientResponse -> movementClient.getMovements(tokenClientResponse.getAccessToken()));
  }

  public Mono<TokenClientResponse> getToken(String clientId, String password) {
    return reactiveRedisTemplate.opsForValue().get(clientId)
            .switchIfEmpty(generateAndSaveToken(clientId, password))
            .flatMap(tokenClientResponse -> generateAndSaveToken(tokenClientResponse, clientId, password));
  }

  private boolean isTokenValid(TokenClientResponse startship){
    return Duration.between(LocalDateTime.now(), startship.getExpireIn()).toSeconds() > 0;
  }

  private Mono<TokenClientResponse> generateAndSaveToken(String clientId, String password){
    return tokenClient.getToken(
                    ClientCredentialRequest.builder()
                            .clientId(clientId)
                            .password(password)
                            .build())
            .flatMap(tokenClientResponse ->
                    reactiveRedisTemplate
                            .opsForValue()
                            .set(clientId, tokenClientResponse)
            )
            .flatMap(booleanMono -> reactiveRedisTemplate.opsForValue().get(clientId));
  }

  private Mono<TokenClientResponse> generateAndSaveToken(TokenClientResponse actualTokenClientResponse, String clientId, String passowrd){
    if (isTokenValid(actualTokenClientResponse)) {
      return Mono.just(actualTokenClientResponse);
    }
    return generateAndSaveToken(clientId, passowrd);
  }

}
