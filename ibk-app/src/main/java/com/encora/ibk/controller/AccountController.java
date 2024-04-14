package com.encora.ibk.controller;

import com.encora.ibk.service.OperationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AccountController {

  OperationService operationService;

  @PostMapping("/deposits")
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<String> deposit() {
    return operationService.deposit();
  }

  @GetMapping("/movements")
  @ResponseStatus(HttpStatus.OK)
  public Mono<String> getMovements() {
    return operationService.getMovements();
  }


}
