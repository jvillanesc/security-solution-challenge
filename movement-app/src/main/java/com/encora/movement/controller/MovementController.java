package com.encora.movement.controller;

import com.encora.movement.exception.BussinesException;
import com.encora.movement.exception.enums.BussinesErrorEnum;
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
public class MovementController {

  @Value("${client-credentials.movement-service.client-id}")
  private String depositServiceClientId;

  @GetMapping("/movements")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> getMovements(@RequestHeader("Authorization") String authorization) {
    validToken(authorization);
    return Mono.just("detail movements");
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
