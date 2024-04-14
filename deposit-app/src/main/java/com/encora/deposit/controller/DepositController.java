package com.encora.deposit.controller;

import com.encora.deposit.exception.BussinesException;
import com.encora.deposit.exception.enums.BussinesErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@Slf4j
public class DepositController {


  @Value("${client-credentials.deposit-service.client-id}")
  private String depositServiceClientId;

  @PostMapping("/deposits")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<String> deposit(@RequestHeader("Authorization") String authorization) {
    validToken(authorization);
    return Mono.just("deposit successful");
  }

  private void validToken(String authorization) {
    log.info("authorization {}", authorization);
    String[] tokenParts = authorization.split("\\|");
    String clienId = tokenParts[0];
    String expireAt = tokenParts[2];
    log.info("expireAt {}", expireAt);
    LocalDateTime dateTime = LocalDateTime.parse(expireAt);
    long expireTime = Duration.between(LocalDateTime.now(), dateTime).toSeconds();
    if (!depositServiceClientId.equals(clienId) || expireTime < 0 ) {
       throw new BussinesException(BussinesErrorEnum.TOKEN_INVALID);
    }
  }

}
